package basic.redis;

import java.util.Iterator;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;

import redis.clients.jedis.Jedis;

public class MyRedisSentinel {
  static String master;
  static final Vector<String> slaveRedisServers = new Vector<String>();
  static final Vector<String> badRedisServers = new Vector<String>();
  
  
  public static void main(String[] args) {
    config("127.0.0.1:6380");
    
    
    new Timer().schedule(new TimerTask(){

      @Override
      public void run() {
        checkMaster();
        
        updateSlaves();
        
        checkBadServer();
        
      }

      }, 1000L,3000L);
    
    
    //开发端口接收请求
    //open();
  }
  
  




  private static void config(String ms) {
    master=ms;
  }

  protected static void checkMaster() {
    System.out.println("检查master状态");
    //获取master ip port
    try {
      Jedis master = new Jedis("mas",1);
      master.ping();
    } catch (Exception e) {
      badRedisServers.add(master);
      changeMaster();
    }
  }


//切换master
  private static void changeMaster() {
   Iterator<String> iterator = slaveRedisServers.iterator();
   while(iterator.hasNext()){
     String slave = iterator.next();
     try {
      //解析ip port
       Jedis jedis = new Jedis("slave",1);
       jedis.slaveofNoOne();
       jedis.close();
       master = slave;
       System.out.println("产生了新的master:"+master);
       break;
    } catch (Exception e) {
      badRedisServers.add(slave);
    }finally{
      iterator.remove();
    }
   }
   
   
   //所有的slave切换到新的master
   for(String slave:slaveRedisServers){
     //解析 
     
     Jedis slaveServer = new Jedis("slave");
     slaveServer.slaveof(master, 1);
     slaveServer.close();
   }
  }






  private static void updateSlaves() {
   //获取所有的slaves
    try {
      String masterhost = master.split(":")[0];
      int masterport = Integer.parseInt(master.split(":")[1]);
      Jedis jedis = new Jedis(masterhost,masterport);
      String info_replication = jedis.info("replication");
      
      //解析返回信息
      String[] lines = info_replication.split("\r\n");
      int slaveCount = Integer.parseInt(lines[2].split(":")[1]);
      
      if(slaveCount>0){
        slaveRedisServers.clear();
        for (int i = 0; i < slaveCount; i++) {
          String port = lines[3+i].split(",")[1].split("=")[1];
          slaveRedisServers.add("127.0.0.1:"+port);
        }
      }
    } catch (Exception e) {
      System.out.println("更新slave失败"+e.getMessage());
    }
  }
  
  
  protected static void checkBadServer() {
    Iterator<String> iterator = badRedisServers.iterator();
    while(iterator.hasNext()){
      try {
        String bad = iterator.next();
        //解析
        Jedis badServer = new Jedis("",1);
        badServer.ping();
        
        
        //如果ping 没有问题，则挂在当前的master下面
        badServer.slaveof(master, 1);
      } catch (Exception e) {
      }
    }
  }
}
