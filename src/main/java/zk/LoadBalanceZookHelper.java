package zk;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.Watcher.Event.EventType;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoadBalanceZookHelper implements Watcher {

  private static Logger logger = LoggerFactory.getLogger(LoadBalanceZookHelper.class);

  private ZooKeeper zk;

  // 可用的服务列表
  private List<String> serverUrlList;

  // 获取service 的索引
  private AtomicInteger index = new AtomicInteger(0);

  // 缓存的BankServerRMIClient
  private Map<String,BankServerRMIClient> bankServerRMIClientCacheMap = new ConcurrentHashMap<String,BankServerRMIClient>();

  public LoadBalanceZookHelper() {
    // 初始化zk
    initZk();
    cacheBankServerRMIClients();
  }

  private static class SingletonHolder {
    static final LoadBalanceZookHelper INSTANCE = new LoadBalanceZookHelper();
  }

  public static LoadBalanceZookHelper getInstance() {
    return SingletonHolder.INSTANCE;
  }

  // 轮询分发服务
  public BankServerRMIClient distributeServer() {
    if (index.get() >= Integer.MAX_VALUE) {
      index.set(0);
    }
    int a = index.get() % serverUrlList.size();
    // 自增长
    index.incrementAndGet();
    String url = serverUrlList.get(a);
    if (logger.isDebugEnabled()) {
      logger.debug("请求的URL：" + url);
    }
    // TODO 删掉syso
    System.out.println("请求的URL：" + url);
    return bankServerRMIClientCacheMap.get(url);

  }

  // 注册rmi ，并缓存
  private void registerRmiAndCache(List<String> serverUrlList) {
    for (String url : serverUrlList) {
      String host[] = url.split(":");
      BankServerRMIClient bankServerRMIClient = new BankServerRMIClient(host[0], Integer.parseInt(host[1]));
      bankServerRMIClientCacheMap.put(url, bankServerRMIClient);
    }
  }

  // 初始化，缓存rmi客户端服务
  public void cacheBankServerRMIClients() {
    // 获取到所有的 serverURL
    serverUrlList = null;//getServerUrlList(BankConfig.getInstance().getBankServerParentNode());
    if (logger.isDebugEnabled()) {
      logger.debug("重新缓存可用的服务列表，serverUrlList：" + serverUrlList);
    }
    if (serverUrlList.size() > 0 && serverUrlList != null) {
      // 注册并缓存
      registerRmiAndCache(serverUrlList);
    } else {
      logger.error("可用的服务列表为空");
      throw new RuntimeException("可用的服务列表为空");
    }
  }

  // 获取zk连接
  private void initZk() {
    try {
      if (zk == null || !zk.getState().isAlive()) {
        synchronized (this) {
          if (zk != null) {
            zk.close();
          }
          // 重新建立连接
          zk = new ZooKeeper("url", 20000, this);
          while (zk.getState() != ZooKeeper.States.CONNECTED) {
            Thread.sleep(3000);
          }
        }
      }
    } catch (Exception e) {
      logger.error("zk初始化连接异常：" + e.toString());
    }
  }

  // 获取可用的服务列表
  public List<String> getServerUrlList(String parentNodePath) {
    List<String> serverList = new ArrayList<String>();
    try {
      // 获取所有子节点
      List<String> nodePathList = zk.getChildren(parentNodePath, true);
      if (nodePathList.size() > 0 && nodePathList != null) {
        for (String hostPath : nodePathList) {
          hostPath = parentNodePath + "/" + hostPath;
          // 获取子节点数据URL
          String data = new String(zk.getData(hostPath, true, null));
          serverList.add(data);
        }
      }
    } catch (KeeperException e) {
      logger.error(e.toString());
    } catch (InterruptedException e) {
      logger.error(e.toString());
    }
    return serverList;
  }

  // 本来有个很简单的办法，直接将原来的缓存去掉，然后在重新注册，但是由于注册rmi底层也是用所 tcp/ip协议，由于这个协议比较耗时，
  // 所以使用以下方式。请大家根据实际情况来处理
  // 节点发生变化时，重新缓存rmi 客户端服务 ，简单来说就是更新缓存
  public void reCacheBankServerRMIClients() {
    // 将原来的serverURL 赋值给
    List<String> oldServerUrlList = serverUrlList;
    // 获取最新的 serverURL,保留住
    serverUrlList = null;//getServerUrlList(BankConfig.getInstance().getBankServerParentNode());
    if (logger.isDebugEnabled()) {
      logger.debug("子节点发生变化，重新获取的服务的URL：" + serverUrlList);
    }
    // 将最新的serverURL赋值给
    List<String> newServerUrlList = serverUrlList;

    List<String> reducedUrlList = new ArrayList<String>();
    reducedUrlList.addAll(oldServerUrlList);
    List<String> incrementUrlList = new ArrayList<String>();
    incrementUrlList.addAll(newServerUrlList);

    // 获取down掉服务的的URL
    reducedUrlList.removeAll(newServerUrlList);
    // 获取新增服务的URL
    incrementUrlList.removeAll(oldServerUrlList);

    if (reducedUrlList.size() > 0 && reducedUrlList != null) {
      for (String reducedUrl : reducedUrlList) {
        // 将down掉的URL的服务从缓存中减掉
        bankServerRMIClientCacheMap.remove(reducedUrl);
      }
      if (logger.isDebugEnabled()) {
        logger.debug("去除掉的服务的URL：" + reducedUrlList);
      }
    }
    if (incrementUrlList.size() > 0 && incrementUrlList != null) {
      // 将新增的URL重新注册rmi 并缓存住
      registerRmiAndCache(incrementUrlList);
      if (logger.isDebugEnabled()) {
        logger.debug("新增的服务的URL：" + incrementUrlList);
      }
    }

  }

  @Override
  public void process(WatchedEvent event) {
    if (event.getState() == KeeperState.Expired) {
      logger.debug("触发了回话过期事件");
      // 重新连接zk
      initZk();
      // 重新缓存
      reCacheBankServerRMIClients();
    }
    // if (event.getState() == KeeperState.SyncConnected) {
    // logger.debug("触发了断开重连事件");
    // // 重新缓存
    // reCacheBankServerRMIClients();
    // }
    if (event.getType() == EventType.NodeChildrenChanged) {
      logger.debug("触发了子节点变化事件");
      // 重新缓存
      reCacheBankServerRMIClients();
    }
  }

  public static void main(String[] args) {
    LoadBalanceZookHelper.getInstance();

  }
}