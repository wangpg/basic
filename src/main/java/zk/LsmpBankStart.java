package zk;

import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import org.apache.commons.lang3.StringUtils;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.ZooKeeper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//import zk.client.config.BankConfig;
//import zk.client.constant.BankConstant;
//import zk.client.zookeeper.BankZookHelper;
//import zk.server.biz.front.rmi.RmiService;
//import zk.server.config.BankDatabaseConfig;
//import zk.server.util.DefaultBeanFactory;

public class LsmpBankStart {

//  private static Logger logger = LoggerFactory.getLogger(LsmpBankStart.class);
//  private ZooKeeper zk;
//
//  public static void main(String[] args) {
//    try {
//      // 启动时，先注册zk节点，重点在这里
//      new LsmpBankStart().registerZook(args[0], args[1]);
//      // 以下为你项目启动的核心代码，这里我项目的server端使用的是rmi协议，可以不用看了
//      DefaultBeanFactory bf = DefaultBeanFactory.getInstance("/spring/springContext_lsmp_bank.xml");
//      RmiService rmiService = (RmiService) bf.getBean("business");
//      int rmiPort = Integer.valueOf(args[1]);
//
//      createRegistry(rmiPort);
//      System.setProperty("java.rmi.server.hostname", args[0]);
//      UnicastRemoteObject.exportObject(rmiService, rmiPort);
//      String rmiUrl = "rmi://" + args[0] + ":" + rmiPort + "/" + args[2];
//      Naming.rebind(rmiUrl, rmiService);
//    } catch (Exception e) {
//      logger.error("LsmpBankStart failed!", e);
//      System.exit(1);
//    }
//  }
//
//  // 初始化zk的连接
//  private void initZk() {
//    try {
//      if (zk == null || !zk.getState().isAlive()) {
//        synchronized (this) {
//          if (zk != null) {
//            zk.close();
//          }
//          // 重新建立连接
//          zk = new ZooKeeper(BankConfig.getInstance().getZookURL(), BankConstant.HA_SESSION_TIMEOUT, this);
//          while (zk.getState() != ZooKeeper.States.CONNECTED) {
//            Thread.sleep(3000);
//          }
//        }
//      }
//    } catch (Exception e) {
//      logger.error("zk初始化连接异常：" + e.toString());
//    }
//  }
//
//  private void registerZook(String ip, String port) {
//    // 初始化zk
//    initZk();
//    // 父节点路径
//    String parentNode = BankConfig.getInstance().getBankServerParentNode();
//    String[] nodeList = parentNode.split("/");
//    String nodePath = "";
//    // 循环创建持久父节点
//    for (String node : nodeList) {
//      if (!StringUtils.isEmpty(node)) {
//        nodePath = nodePath + "/" + node;
//        BankZookHelper.createNode(zk, nodePath, node, CreateMode.PERSISTENT);
//      }
//    }
//    // 创建临时子节点
//    // BankZookHelper为封装的zookeeper一些基础API的类
//    BankZookHelper.createNode(zk, nodePath + "/" + ip, ip + ":" + port, CreateMode.EPHEMERAL);
//  }

}