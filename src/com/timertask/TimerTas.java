package com.timertask;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.stream.Collectors;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.b2cso_main.model.*;
import com.b2cso_detail.model.*;
import com.bid_detail.model.BidDetailService;
import com.bid_detail.model.BidDetailVO;
import com.bid_main.model.BidMainService;
import com.bid_main.model.BidMainVO;
import com.mail.model.MailService;
import com.mem.model.*;

public class TimerTas implements ServletContextListener{
	Timer timer = null;
	
	public void contextInitialized(ServletContextEvent sce) {
		final TimerTask tk;
		timer = new Timer();
		tk = new TimerTask() {
			BidMainService bidMainSvc = new BidMainService();
			BidDetailService bidDetailSvc = new BidDetailService();
			MemService memSvc = new MemService();
			B2cso_mainService soMainSvc = new B2cso_mainService();
			B2cso_detailService soDetailSvc = new B2cso_detailService();
			
			
			public void run() {
				List<BidMainVO> normalBid = null;
			
				// 判斷是正常的競標
				normalBid = bidMainSvc.getAll().stream().filter(e -> e.getStatus().equals(1)).collect(Collectors.toList());
				final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				
				long endTimeLong = 0;
				
				//取得現在時間的毫秒
				Long currentTimeLong = System.currentTimeMillis();

				for(BidMainVO bmVO : normalBid) {
//					System.out.println(bmVO.getBid_id());
					String endTime = formatter.format(bmVO.getEnd_time());
					try {
						Date endDate = formatter.parse(endTime);
						endTimeLong = endDate.getTime();
					}catch(Exception e) {
						
					}
					long expiryTime = endTimeLong - currentTimeLong;
					// 競標截止
					String bid_id = bmVO.getBid_id();
//					System.out.println("bid_id="+bid_id);
//					System.out.println("expiryTime="+expiryTime);
//					System.out.println("winner="+bmVO.getWinner());
//					System.out.println("=====================");
					
					if (expiryTime <= 0 && bmVO.getWinner() == null) {

						List<BidDetailVO> aBidDetail = bidDetailSvc.getByBidId(bid_id);
						
						if(aBidDetail.size() != 0) {
						
						BidDetailVO lastbdVO = aBidDetail.get(aBidDetail.size() - 1);
						BidMainService.updateWinner(bid_id, 2, lastbdVO.getMem_id());
						System.out.println("aBidDetail!=0, bid_id="+bid_id);
						System.out.println("update winner="+bmVO.getWinner());
						System.out.println("==============");
						
						// 成立訂單
						soMainSvc.insert(3, lastbdVO.getMem_id(), "BST0001", 1, lastbdVO.getBid_price(), "無", "無", 1);
						String so_id = soMainSvc.getLatestSoId();
						System.out.println("so_id="+so_id);
						soDetailSvc.addDetail(lastbdVO.getBid_price(),bmVO.getPro_id(), 1, so_id);
						
						// 發送競標成功信件
						MailService mailService = new MailService();
						
						String subject = "恭喜您競標商品成功!";
						MemVO winMemVO = memSvc.getOneMem(lastbdVO.getMem_id());
						String messageText = "Hello! " + (winMemVO.getFirst_name() + winMemVO.getLast_name()) + 
								"\n您在" + bid_id + "已競標成功， 訂單編號為"+ so_id + "， \n請在一天內到會員中心完成商品訂單並付款， 否則將取消您的得標者資格。 "; 
						
						mailService.sendMail(winMemVO.getEmail(), subject, messageText);
						}
					}
					
				}
			}
		};
		timer.scheduleAtFixedRate(tk, 0, 1000);
	}
	
	public void contextDestroyed(ServletContextEvent sce) {
		timer.cancel();
	}
	
}
