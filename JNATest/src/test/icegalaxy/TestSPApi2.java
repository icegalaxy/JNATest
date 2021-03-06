package test.icegalaxy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.sun.jna.Callback;
import com.sun.jna.Library;

import com.sun.jna.Native;

import com.sun.jna.Structure;
import com.sun.jna.win32.StdCallLibrary;

import test.icegalaxy.TestSPApi2.SPApiDll.SPApiAccInfo;
import test.icegalaxy.TestSPApi2.SPApiDll.SPApiOrder;
import test.icegalaxy.TestSPApi2.SPApiDll.SPApiPrice;
import test.icegalaxy.TestSPApi2.SPApiDll.SPApiTrade;

public class TestSPApi2
{

	static int counter;
	static long status = 0;
	public static double currentBid;
	public static double currentAsk;

	static byte[] product = getBytes("CLQ7", 16);

	static int port = 8080;
	static String license = "58BA6E2F967DE";
	static String app_id = "SPDEMO";
	static String userid = "DEMO201703047";
	static String password = "830811aa";
	static String server = "demo.spsystem.info";
	
/*	static int port = 8080;
	static String license = "58A665DE84D02";
	static String app_id = "SPDEMO";
	static String userid = "DEMO201702141";
	static String password = "00000000";
	static String server = "demo.spsystem.info";*/

	public interface SPApiDll extends StdCallLibrary
	{
		SPApiDll INSTANCE = (SPApiDll) Native.loadLibrary("spapidllm64.dll", SPApiDll.class);
		
		int SPAPI_SetLanguageId(int langid);

		int SPAPI_Initialize();

		int SPAPI_Uninitialize(String user_id);

		void SPAPI_GetAllTrades(String user_id, String acc_no, ArrayList<SPApiTrade> trades);
		
		int SPAPI_DeleteAllOrders(String user_id, String acc_no);

		int SPAPI_ActivateAllOrders(String user_id, String acc_no);

		int SPAPI_GetAccInfo(String user_id, SPApiAccInfo acc_info);

		int SPAPI_GetPriceByCode(String user_id, byte[] prod_code, SPApiPrice price);

		void SPAPI_SetLoginInfo(String server, int port, String license, String app_id, String userid, String password);

		int SPAPI_Login();

		int SPAPI_Logout(String user_id);

		int SPAPI_AddOrder(SPApiOrder order);

		int SPAPI_SubscribePrice(String user_id, byte[] prod_code, int mode);

		void SPAPI_RegisterTradeReport(RegisterTradeReport tradeReport);

		void SPAPI_RegisterOrderRequestFailed(RegisterOrderFail orderFail);

		void SPAPI_RegisterOrderBeforeSendReport(RegisterOrderB4 orderB4);

		void SPAPI_RegisterApiPriceUpdate(RegisterPriceUpdate priceUpdate);

		void SPAPI_RegisterConnectingReply(RegisterConn conn);


		void SPAPI_RegisterLoginReply(RegisterLoginReply register);

		void SPAPI_RegisterLoginStatusUpdate(RegisterLoginStatusUpdate update);

		public class SPApiTrade extends Structure
		{
			public double RecNo;
			public double Price;
			public double AvgPrice;
			public long TradeNo;
			public long ExtOrderNo;
			public int IntOrderNo;
			public int Qty;
			public int TradeDate;
			public int TradeTime;
			public byte[] AccNo = new byte[16];
			public byte[] ProdCode = new byte[16];
			public byte[] Initiator = new byte[16];
			public byte[] Ref = new byte[16];
			public byte[] Ref2 = new byte[16];
			public byte[] GatewayCode = new byte[16];
			public byte[] ClOrderId = new byte[40];
			public byte BuySell;
			public byte OpenClose;
			public byte Status;
			public byte DecInPrice;
			public double OrderPrice;
			public byte[] TradeRef = new byte[40];
			public int TotalQty;
			public int RemainingQty;
			public int TradedQty;
			public double AvgTradedPrice;

			@Override
			protected List getFieldOrder()
			{
				return Arrays.asList(new String[]
				{ "RecNo", "Price", "AvgPrice", "TradeNo", "ExtOrderNo", "IntOrderNo", "Qty", "TradeDate", "TradeTime",
						"AccNo", "ProdCode", "Initiator", "Ref", "Ref2", "GatewayCode", "ClOrderId", "BuySell",
						"OpenClose", "Status", "DecInPrice", "OrderPrice", "TradeRef", "TotalQty", "RemainingQty",
						"TradedQty", "AvgTradedPrice" });
			}
		}

		public class SPApiAccInfo extends Structure
		{

			public double NAV;
			public double BuyingPower, CashBal, MarginCall, CommodityPL, LockupAmt, CreditLimit, MaxMargin,
					MaxLoanLimit, TradingLimit, RawMargin, IMargin, MMargin, TodayTrans, LoanLimit, TotalFee, LoanToMR,
					LoanToMV;
			// char[] AccName = new char[16];
			public byte[] AccName = new byte[16];
			public byte[] BaseCcy = new byte[4];
			public byte[] MarginClass = new byte[16];
			public byte[] TradeClass = new byte[16];
			public byte[] ClientId = new byte[16];
			public byte[] AEId = new byte[16];
			public byte AccType;
			public byte CtrlLevel;
			public byte Active;
			public byte MarginPeriod;

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
			public byte[] ProdCode = new byte[16];
			public byte[] ProdName = new byte[40];
			public byte DecInPrice;
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
			public long ExtOrderNo;
			public int IntOrderNo;
			public int Qty;
			public int TradedQty;
			public int TotalQty;
			public int ValidTime;
			public int SchedTime;
			public int TimeStamp;
			public int OrderOptions;
			public byte[] AccNo = new byte[16];
			public byte[] ProdCode = new byte[16];
			public byte[] Initiator = new byte[16];
			public byte[] Ref = new byte[16];
			public byte[] Ref2 = new byte[16];
			public byte[] GatewayCode = new byte[16];
			public byte[] ClOrderId = new byte[40];
			public byte BuySell;
			public byte StopType;
			public byte OpenClose;
			public byte CondType;
			public byte OrderType;
			public byte ValidType;
			public byte Status;
			public byte DecInPrice;
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

	public interface RegisterOrderB4 extends Callback
	{
		void invoke(SPApiOrder order);
	}

	public interface RegisterOrderFail extends Callback
	{
		void invoke(int action, SPApiOrder order, long err_code, String err_msg);
	}

	public interface RegisterTradeReport extends Callback
	{
		void invoke(long rec_no, SPApiTrade trade);
	}

	

	public interface RegisterPriceUpdate extends Callback
	{
		void invoke(SPApiPrice price);
	}

	public interface RegisterConn extends Callback
	{
		void invoke(long host_type, long con_status);
	}

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

	public static int getAccInfo()
	{

		SPApiAccInfo info = new SPApiAccInfo();

		int i = SPApiDll.INSTANCE.SPAPI_GetAccInfo(userid, info);

		System.out.println("AEID: " + Native.toString(info.AEId));
		System.out.println("ClientID: " + Native.toString(info.ClientId));
		System.out.println("AccName: " + Native.toString(info.AccName));
		System.out.println("Buying Power: " + info.BuyingPower);

		return i;

	}

	public static int getPriceByCode()
	{
		SPApiPrice price = new SPApiPrice();
		int i = SPApiDll.INSTANCE.SPAPI_GetPriceByCode(userid, product, price);
		System.out.println("Get price by code: " + price.Last[0] + ", Open: " + price.Open);
		currentBid = price.Bid[0];
		currentAsk = price.Ask[0];
		return i;
	}

	public static int addOrder(byte buy_sell)
	{

		
		int rc;
		SPApiOrder order = new SPApiOrder();

		// order.AccNo = Native.toByteArray("");
		// order.ProdCode = Native.toByteArray("CLJ7");
		setBytes(order.AccNo, userid);

		order.ProdCode = product; // need the replace necessary byte one by one,
									// not setting the whole new array

		// order.Initiator = Native.toByteArray(userid + " ");
		setBytes(order.Initiator, userid);

		order.BuySell = buy_sell;

		order.Qty = 2;

		setBytes(order.Ref, "Java");
		setBytes(order.Ref2, "SPAPI");
		// order.Ref2 = Native.toByteArray("0");
		// order.GatewayCode = Native.toByteArray("");
		setBytes(order.GatewayCode, "");

		order.CondType = 0; // normal type
		// order.ClOrderId = Native.toByteArray("0");
		setBytes(order.ClOrderId, "2");
		order.ValidType = 0;

		order.DecInPrice = 2;
		// order.OpenClose = '\0';
		// order.StopType = '0';
		order.OrderType = 0; // limit
		// order.OrderOptions = 0;

		getPriceByCode();

		if (buy_sell == 'B')
			order.Price = currentAsk; // market price
		else
			order.Price = currentBid;
		
	/*	if (buy_sell == 'B')
			order.Price = 50; // market price
		else
			order.Price = 60;*/

		rc = SPApiDll.INSTANCE.SPAPI_AddOrder(order);

		System.out.println("Add order: " + buy_sell + "[" + rc + "]");

		// System.out.println("Activate all orders: [" +
		// SPApiDll.INSTANCE.SPAPI_ActivateAllOrders(userid, userid) + "]");

		// System.out.println("order status: " + order.Status);

		return rc;
		// if (rc == 0) { if (DllShowTextData != null) DllShowTextData("Add
		// Order Success!"); }
		// else { if (DllShowTextData != null) DllShowTextData("Add Order
		// Failure! " + rc.ToString()); }

	}

	public static void displayAllTrades()
	{
		ArrayList<SPApiTrade> trades = null;

		SPApiDll.INSTANCE.SPAPI_GetAllTrades(userid, userid, trades);

		System.out.println("Trying to display all trades");

		for (int i = 0; i < trades.size(); i++)
		{

			System.out.println("Rec No: " + trades.get(i).RecNo + ", Price: " + trades.get(i).Price + ", BuySell: "
					+ trades.get(i).BuySell);

		}

	}

	public static void main(String[] args)
	{
		int lan = 1;
		int in = 1;
		int un = 1;
		int price = 1;
		int login = 1;
		int logout = 1;
		
		
		lan = SPApiDll.INSTANCE.SPAPI_SetLanguageId(0);
		System.out.println("Lanid: " + lan);
		
		in = SPApiDll.INSTANCE.SPAPI_Initialize();
		
		System.out.println("Init");

//		SPApiDll.INSTANCE.SPAPI_SetLoginInfo(server, port, license, app_id, userid, password);

//		login = SPApiDll.INSTANCE.SPAPI_Login();

			
		while (true)
		{
			sleep(1000);
			counter++;
			if (counter > 3)
				break;
		}


//		logout = SPApiDll.INSTANCE.SPAPI_Logout(userid);

//		while (logout != 0)
//		{
//			sleep(1000);
//
//			System.out.println("Logout: " + logout);
//		}

		System.out.println("init: " + in);
//		System.out.println("login: " + login);

//		System.out.println("logout: " + logout);

		SPApiDll.INSTANCE.SPAPI_Uninitialize(userid);

		// System.out.println("init: " + in);
		// System.out.println("login: " + login);
		//
		// System.out.println("logout: " + logout);
		System.out.println("uninit ");

	}

	public static void setBytes(byte[] bytes, String s)
	{
		for (int i = 0; i < s.length(); i++)
		{
			bytes[i] = (byte) s.charAt(i);
		}

	}

	public static byte[] getBytes(String s, int size)
	{

		byte[] bytes = new byte[size];

		for (int i = 0; i < s.length(); i++)
		{
			bytes[i] = (byte) s.charAt(i);

		}
		return bytes;

	}

	public static void sleep(int i)
	{
		try
		{
			Thread.sleep(i);
		} catch (InterruptedException e)
		{
			e.printStackTrace();
		}
	}
}
