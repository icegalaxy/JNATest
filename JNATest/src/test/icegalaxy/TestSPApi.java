package test.icegalaxy;

import java.util.Arrays;
import java.util.List;

import javax.imageio.spi.RegisterableService;

import com.sun.jna.Callback;
import com.sun.jna.Library;

import com.sun.jna.Native;

import com.sun.jna.Structure;

import test.icegalaxy.TestSPApi.SPApiDll.AccLoginReply;
import test.icegalaxy.TestSPApi.SPApiDll.RegisterConn;
import test.icegalaxy.TestSPApi.SPApiDll.RegisterError;
import test.icegalaxy.TestSPApi.SPApiDll.RegisterLoginReply;
import test.icegalaxy.TestSPApi.SPApiDll.RegisterPriceUpdate;
import test.icegalaxy.TestSPApi.SPApiDll.RegisterTradeReport;
import test.icegalaxy.TestSPApi.SPApiDll.SPApiAccInfo;
import test.icegalaxy.TestSPApi.SPApiDll.SPApiOrder;
import test.icegalaxy.TestSPApi.SPApiDll.SPApiPrice;
import test.icegalaxy.TestSPApi.SPApiDll.SPApiTrade;

public class TestSPApi
{

	static int counter;
	static long status = 0;
	public static double currentBid;
	public static double currentAsk;

	// int port = 8080;
	// String license = "76C2FB5B60006C7A";
	// String app_id = "BS";
	// String userid = "T865829";
	// String password = "ting1980";
	// String server = "futures.bsgroup.com.hk";

	static int port = 8080;
	static String license = "58A665DE84D02";
	static String app_id = "SPDEMO";
	static String userid = "DEMO201702141";
	static String password = "00000000";
	static String server = "demo.spsystem.info";

	public interface SPApiDll extends Library
	{
		SPApiDll INSTANCE = (SPApiDll) Native.loadLibrary("spapidllm64.dll", SPApiDll.class);

		int SPAPI_Initialize();

		void SPAPI_Uninitialize();
		
		int SPAPI_GetAccInfo(String user_id, SPApiAccInfo acc_info);

		int SPAPI_GetPriceByCode(String user_id, String prod_code, SPApiPrice price);

		void SPAPI_SetLoginInfo(String server, int port, String license, String app_id, String userid, String password);

		int SPAPI_Login();

		int SPAPI_Logout(String user_id);

		int SPAPI_AddOrder(SPApiOrder order);

		int SPAPI_SubscribePrice(String user_id, String prod_code, int mode);

		void SPAPI_RegisterTradeReport(RegisterTradeReport tradeReport);

		// void SPAPI_RegisterLoadTradeReadyPush(RegisterTradeReport
		// tradeReport);

		void SPAPI_RegisterApiPriceUpdate(RegisterPriceUpdate priceUpdate);

		void SPAPI_RegisterConnectingReply(RegisterConn conn);

		void SPAPI_RegisterAccountLoginReply(AccLoginReply loginReply);

		// void SPAPI_RegisterTradeReport(RegisterTradeReport tradeReport);

		void SPAPI_RegisterLoginReply(RegisterLoginReply register);

		void SPAPI_RegisterLoginStatusUpdate(RegisterLoginStatusUpdate update);

		// f void SPAPI_RegisterConnectionErrorUpdate(RegisterError error);

		public interface RegisterTradeReport extends Callback
		{
			void invoke(long rec_no, SPApiTrade trade);
		}

		public interface AccLoginReply extends Callback
		{
			void invoke(String accNo, long ret_code, String ret_msg);
		}

		public interface RegisterPriceUpdate extends Callback
		{
			void invoke(SPApiPrice price);
		}

		public interface RegisterConn extends Callback
		{
			void invoke(long host_type, long con_status);
		}

		// public interface RegisterTradeReport extends Callback
		// {
		// void invoke(String acc_no);
		// }

		public interface RegisterError extends Callback
		{
			void invoke(short host_id, long link_err);
		}

		public interface RegisterLoginReply extends Callback
		{
			void printLoginStatus(long ret_code, String ret_msg);
		}

		public interface RegisterLoginStatusUpdate extends Callback
		{
			void printStatus(long login_status);
		}

		public class SPApiTrade extends Structure
		{
			public double RecNo;
			public double Price;
			public double AvgPrice;
			public int TradeNo;
			public int ExtOrderNo;
			public int IntOrderNo;
			public int Qty;
			public int TradeDate;
			public int TradeTime;
			public char[] AccNo = new char[16];
			public char[] ProdCode = new char[16];
			public char[] Initiator = new char[16];
			public char[] Ref = new char[16];
			public char[] Ref2 = new char[16];
			public char[] GatewayCode = new char[16];
			public char[] ClOrderId = new char[40];
			public char BuySell;
			public char OpenClose;
			public int Status;
			public int DecInPrice;
			public double OrderPrice;
			public char[] TradeRef = new char[40];
			public int TotalQty;
			public int RemainingQty;
			public int TradedQty;
			public double AvgTradedPrice;

			@Override
			protected List getFieldOrder()
			{
				// TODO Auto-generated method stub
				return Arrays.asList(new String[]
				{ "RecNo", "Price", "AvgPrice", "TradeNo", "ExtOrderNo", "IntOrderNo", "Qty", "TradeDate", "TradeTime",
						"AccNo", "", "ProdCode", "Initiator", "Ref", "Ref2", "GatewayCode", "ClOrderId", "BuySell",
						"OpenClose", "Status", "DecInPrice", "OrderPrice", "TradeRef", "TotalQty", "RemainingQty",
						"TradedQty", "AvgTradedPrice" });
			}
		}

		public class SPApiAccInfo extends Structure
		{

			public double NAV;
			public double BuyingPower, CashBal, MarginCall, CommodityPL, LockupAmt, CreditLimit, MaxMargin, MaxLoanLimit,
					TradingLimit, RawMargin, IMargin, MMargin, TodayTrans, LoanLimit, TotalFee, LoanToMR, LoanToMV;
//			char[] AccName = new char[16];
			public byte[] AccName = new byte[16];
			public byte[] BaseCcy = new byte[4];
			public byte[] MarginClass = new byte[16];
			public byte[] TradeClass = new byte[16];
			public byte[] ClientId = new byte[16];
			public byte[] AEId = new byte[16];
			public String AccType;
			public String CtrlLevel;
			public String Active;
			public String MarginPeriod;

			@Override
			protected List getFieldOrder()
			{
				return Arrays.asList(new String[]
				{ "NAV", "BuyingPower", "CashBal", "MarginCall", "CommodityPL", "LockupAmt", "CreditLimit", "MaxMargin",
						"MaxLoanLimit", "TradingLimit", "RawMargin", "IMargin", "MMargin", "TodayTrans", "LoanLimit",
						"TotalFee", "LoanToMR", "LoanToMV", "AccName", "BaseCcy", "MarginClass", "TradeClass",
						"ClientId", "AEId", "AccType", "CtrlLevel", "Active", "MarginPeriod" });
			}

		}

		public class SPApiPrice extends Structure
		{

			// public static class ByReference extends SPApiPrice implements
			// Structure.ByReference{}

			public double[] Bid = new double[20];
			public int[] BidQty = new int[20];
			public int[] BidTicket = new int[20];
			public double[] Ask = new double[20];
			public int[] AskQty = new int[20];
			public int[] AskTicket = new int[20];
			public double[] Last = new double[20];
			public int[] LastQty = new int[20];
			public int[] LastTime = new int[20];
			public double Equil;
			public double Open;
			public double High;
			public double Low;
			public double Close;
			public int CloseDate;
			public double TurnoverVol;
			public double TurnoverAmt;
			public int OpenInt;
			public char[] ProdCode = new char[16];
			public char[] ProdName = new char[40];
			public String DecInPrice;
			public int ExstateNo;
			public int TradeStateNo;
			public boolean Suspend;
			public int ExpiryYMD;
			public int ContractYMD;
			public int Timestamp;

			@Override
			protected List getFieldOrder()
			{
				return Arrays.asList(new String[]
				{ "Bid", "BidQty", "BidTicket", "Ask", "AskQty", "AskTicket", "Last", "LastQty", "LastTime", "Equil",
						"Open", "High", "Low", "Close", "CloseDate", "TurnoverVol", "TurnoverAmt", "OpenInt",
						"ProdCode", "ProdName", "DecInPrice", "ExstateNo", "TradeStateNo", "Suspend", "ExpiryYMD",
						"ContractYMD", "Timestamp" });
			}

		}

		public class SPApiOrder extends Structure
		{

			public double Price;
			public double StopLevel;
			public double UpLevel;
			public double UpPrice;
			public double DownLevel;
			public double DownPrice;
			public int ExtOrderNo;
			public int IntOrderNo;
			public int Qty;
			public int TradedQty;
			public int TotalQty;
			public int ValidTime;
			public int SchedTime;
			public int TimeStamp;
			public long OrderOptions;
			public byte[] AccNo = new byte[16];
			public byte[] ProdCode = new byte[16];
			public byte[] Initiator = new byte[16];
			public byte[] Ref = new byte[16];
			public byte[] Ref2 = new byte[16];
			public byte[] GatewayCode = new byte[16];
			public byte[] ClOrderId = new byte[40];
			public byte BuySell;
			public char StopType;
			public char OpenClose;
			public int CondType;
			public int OrderType;
			public int ValidType;
			public int Status;
			public int DecInPrice;
			public int OrderAction;
			public int updateTime;
			public int updateSeqNo;

			@Override
			protected List getFieldOrder()
			{
				return Arrays.asList(new String[]
				{ "Price", "StopLevel", "UpLevel", "UpPrice", "DownLevel", "DownPrice", "ExtOrderNo", "IntOrderNo",
						"Qty", "TradedQty", "TotalQty", "ValidTime", "SchedTime", "TimeStamp", "OrderOptions", "AccNo",
						"ProdCode", "Initiator", "Ref", "Ref2", "GatewayCode", "ClOrderId", "BuySell", "StopType",
						"OpenClose", "CondType", "OrderType", "ValidType", "Status", "DecInPrice", "OrderAction",
						"updateTime", "updateSeqNo" });
			}

		}
	}
	
	public static int getAccInfo()
	{
		
		SPApiAccInfo info = new SPApiAccInfo();
		
		int i = SPApiDll.INSTANCE.SPAPI_GetAccInfo(userid, info);
		
		System.out.println("AEID: " + Native.toString(info.AEId));
		System.out.println("ClientID: " + Native.toString(info.ClientId));
		System.out.println("AccName: " + Native.toString(info.AccName));
		
		return i;
		
	}

	public static int getPriceByCode()
	{
		SPApiPrice price = new SPApiPrice();
		int i = SPApiDll.INSTANCE.SPAPI_GetPriceByCode(userid, "CLJ7", price);
		System.out.println("Get price by code: " + price.Last[0] + ", Open: " + price.Open);
		currentBid = price.Bid[0];
		currentAsk = price.Ask[0];
		return i;
	}

	public static int addOrder(byte buy_sell)
	{
		
	
		
		int rc;
		SPApiOrder order = new SPApiOrder();

		order.AccNo = Native.toByteArray(userid);
		order.Initiator =  Native.toByteArray(userid);
		order.BuySell = buy_sell;

		order.Qty = 2;

		order.ProdCode = Native.toByteArray("CLJ7");

		order.Ref = Native.toByteArray(userid);
		order.Ref2 = Native.toByteArray("0");
		order.GatewayCode = Native.toByteArray("");

		order.CondType = 0; // normal type
		order.ClOrderId = Native.toByteArray("0");
		order.ValidType = 0;
		order.DecInPrice = 2;

		order.OrderType = 0; // limit
		
		
		System.out.println("order.Initiator: " + Native.toString(order.Initiator));

	//	if (new Char(buy_sell) == 'B')
	//		order.Price = currentAsk; // market price
	//	else
			order.Price = currentBid;

		rc = SPApiDll.INSTANCE.SPAPI_AddOrder(order);

		System.out.println("Add order: " + buy_sell + "[" + rc + "]");

		return rc;
		// if (rc == 0) { if (DllShowTextData != null) DllShowTextData("Add
		// Order Success!"); }
		// else { if (DllShowTextData != null) DllShowTextData("Add Order
		// Failure! " + rc.ToString()); }

	}

	public static void main(String[] args)
	{

		int in = 1;
		int un = 1;
		int price = 1;
		int login = 1;
		int logout = 1;

		RegisterTradeReport tradeReport = new RegisterTradeReport()
		{

			@Override
			public void invoke(long rec_no, SPApiTrade trade)
			{
				System.out.println("Rec_no: " + rec_no + ", Price: " + trade.Price);
			}
		};

		AccLoginReply accReply = new AccLoginReply()
		{

			@Override
			public void invoke(String accNo, long ret_code, String ret_msg)
			{
				System.out.println("AccNo: " + accNo);
			}
		};

		RegisterPriceUpdate priceUpdate = new RegisterPriceUpdate()
		{

			@Override
			public void invoke(SPApiPrice price)
			{
				System.out.println("Lastest Deal: " + price.Last[0]);

			}

		};

		RegisterConn conn = new RegisterConn()
		{

			@Override
			public void invoke(long host_type, long con_status)
			{
				System.out.println("conn reply- host type: " + host_type + ", con state: " + con_status);
				status += con_status;
			}
		};

		RegisterError error = new RegisterError()
		{

			@Override
			public void invoke(short host_id, long link_err)
			{
				System.out.println("Error: " + link_err);

			}
		};

		RegisterLoginReply loginReply = new RegisterLoginReply()
		{

			@Override
			public void printLoginStatus(long ret_code, String ret_msg)
			{
				System.out.println("Login status: " + ret_code);

			}
		};

		in = SPApiDll.INSTANCE.SPAPI_Initialize();

		// SPApiDll.INSTANCE.SPAPI_RegisterLoginReply(loginReply);

		SPApiDll.INSTANCE.SPAPI_RegisterApiPriceUpdate(priceUpdate);

		SPApiDll.INSTANCE.SPAPI_RegisterTradeReport(tradeReport);

		// SPApiDll.INSTANCE.SPAPI_RegisterLoginStatusUpdate(update);

		// SPApiDll.INSTANCE.SPAPI_RegisterConnectionErrorUpdate(error);

		SPApiDll.INSTANCE.SPAPI_RegisterConnectingReply(conn);

		SPApiDll.INSTANCE.SPAPI_RegisterAccountLoginReply(accReply);

		SPApiDll.INSTANCE.SPAPI_SetLoginInfo(server, port, license, app_id, userid, password);

		login = SPApiDll.INSTANCE.SPAPI_Login();

		while (status < 9)

		{

			try
			{
				Thread.sleep(1000);
			} catch (InterruptedException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		price = SPApiDll.INSTANCE.SPAPI_SubscribePrice(userid, "CLJ7", 1);

		System.out.println("Price subscribed: " + price);

	
		byte b = 102;

		addOrder(b);

		while (true)
		{
			try
			{
				Thread.sleep(1000);
			} catch (InterruptedException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			counter++;
			System.out.println("counter: " + counter);
			if (counter > 10)
				break;
		}
		
		getPriceByCode();
		
		System.out.println("AccInfo: " + getAccInfo());

		byte s = 83;
		
		addOrder(s);

		price = SPApiDll.INSTANCE.SPAPI_SubscribePrice(userid, "CLJ7", 0);
		try
		{
			Thread.sleep(1000);
		} catch (InterruptedException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		logout = SPApiDll.INSTANCE.SPAPI_Logout(userid);

		while (logout != 0)
		{
			try
			{
				Thread.sleep(1000);
			} catch (InterruptedException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			System.out.println("Logout: " + logout);
		}

		System.out.println("init: " + in);
		System.out.println("login: " + login);

		System.out.println("logout: " + logout);

		 SPApiDll.INSTANCE.SPAPI_Uninitialize();

		// System.out.println("init: " + in);
		// System.out.println("login: " + login);
		//
		// System.out.println("logout: " + logout);
		System.out.println("uninit: " + un);

	}

}
