package test.icegalaxy;

import java.util.ArrayList;
import test.icegalaxy.SPApi;
import java.util.List;

import com.sun.jna.Callback;
import com.sun.jna.Library;

import com.sun.jna.Native;

import com.sun.jna.Structure;
import com.sun.jna.win32.StdCallLibrary;
import com.sun.jna.win32.StdCallLibrary.StdCallCallback;


public class TestSPApi4
{

	static int counter;
	static long status = 0;
	public static double currentBid;
	public static double currentAsk;

	static byte[] product = getBytes("CLQ7", 16);

	SPApi api;
	
	public static int getAccInfo(SPApi api)
	{
		
		api.SPApiAccInfo info = new 	api.SPApiAccInfo();

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

	public static SPApiOrder addOrder(byte buy_sell)
	{

//		
//		int rc;
		SPApiOrder order = new SPApiOrder();

		setBytes(order.AccNo, userid);

		order.ProdCode = product; // need the replace necessary byte one by one,
									// not setting the whole new array

		setBytes(order.Initiator, userid);

		order.BuySell = buy_sell;
	
		order.Qty = 2;

		setBytes(order.Ref, "Java");
		setBytes(order.Ref2, "SPAPI");
		setBytes(order.GatewayCode, "");
		

		order.CondType = 0; // normal type
		setBytes(order.ClOrderId, "2");
		order.ValidType = 0;

		order.DecInPrice = 2;
		
		order.StopType = 'L';
		order.OrderType = 0; // limit
		order.OrderOptions = 0;

		getPriceByCode();

		if (buy_sell == 'B')
		{
			order.Price = currentAsk; // market price
	//		order.OpenClose = 'O';
			order.OpenClose = '\0';
		}
		else
		{
			order.Price = currentBid;
	//		order.OpenClose = 'C';
			order.OpenClose = '\0';
		}
		
	/*	if (buy_sell == 'B')
			order.Price = 50; // market price
		else
			order.Price = 60;*/

	

		// System.out.println("Activate all orders: [" +
		// SPApiDll.INSTANCE.SPAPI_ActivateAllOrders(userid, userid) + "]");

		// System.out.println("order status: " + order.Status);

		return order;
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

		int in = 1;
		int un = 1;
		int price = 1;
		int login = 1;
		int logout = 1;
		
		api = SPApi.INSTANCE;
		
		
		RegisterOrder orderReport = (rec, order) -> System.out.println("Order report, Rec no: " + rec + ", Price: " + order.Price);
		
		RegisterOrderB4 orderB4 = (orderB4x) -> System.out.println("Order status b4: " + orderB4x.Status);

		RegisterTradeReport tradeReport = (rec_no, trade) -> System.out.println("Rec_no: " + rec_no + ", Price: " + trade.Price);
		
		RegisterLoginReply loginReply = (ret_code, ret_msg) -> System.out.println("Login reply: " + ret_msg + " [" + ret_code + "]");
		//Try using lambda
		RegisterPriceUpdate priceUpdate = (last) -> System.out.print(last.Last[0] + ", Dec place: " + last.DecInPrice + " " );
	

		RegisterConn conn = (host_type,  con_status) ->
		{
			System.out.println("conn reply- host type: " + host_type + ", con state: " + con_status);
			status += con_status;
		};
				
		RegisterOrderFail orderFail = (action, order, err_code, err_msg) -> System.out.println("Action no: " + action + 
				", order status: " + order.Status + ", dec place: " + order.DecInPrice + ", Error msg: " + err_msg);

		in = SPApiDll.INSTANCE.SPAPI_Initialize();

		// SPApiDll.INSTANCE.SPAPI_RegisterLoginReply(loginReply);

		SPApiDll.INSTANCE.SPAPI_RegisterApiPriceUpdate(priceUpdate);

		SPApiDll.INSTANCE.SPAPI_RegisterTradeReport(tradeReport);
		
		SPApiDll.INSTANCE.SPAPI_RegisterOrderRequestFailed(orderFail);
		
		SPApiDll.INSTANCE.SPAPI_RegisterOrderBeforeSendReport(orderB4);

		// SPApiDll.INSTANCE.SPAPI_RegisterLoginStatusUpdate(update);

		// SPApiDll.INSTANCE.SPAPI_RegisterConnectionErrorUpdate(error);

		SPApiDll.INSTANCE.SPAPI_RegisterConnectingReply(conn);
		
		SPApiDll.INSTANCE.SPAPI_RegisterOrderReport(orderReport);

		SPApiDll.INSTANCE.SPAPI_RegisterLoginReply(loginReply);

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
		
		System.out.println("Test 1");

		price = SPApiDll.INSTANCE.SPAPI_SubscribePrice(userid, product, 1);

		System.out.println("Price subscribed: " + price);
		

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
		//	System.out.println("counter: " + counter);
			if (counter > 2)
				break;
		}

		System.out.println("Test 2");
		
		System.out.println("AccInfo: " + getAccInfo());

		System.out.println("Test 3");
		
		SPApiDll.INSTANCE.SPAPI_AddOrder(addOrder((byte) 'B'));

		System.out.println("Add order:  B");

		System.out.println("Test 4");
		
		counter = 0;
		
		while (true)
		{
			sleep(1000);
			counter++;
			if (counter > 10)
				break;
		}
	//	SPApiDll.INSTANCE.SPAPI_DeleteAllOrders(userid, userid);
//		getPriceByCode();

		System.out.println("AccInfo: " + getAccInfo());

		SPApiDll.INSTANCE.SPAPI_AddOrder(addOrder((byte) 'S'));

		System.out.println("Add order:  B");
		
		counter = 0;
		
		while (true)
		{
			sleep(1000);
			counter++;
			if (counter > 10)
				break;
		}
		
		System.out.println("AccInfo: " + getAccInfo());
//		displayAllTrades();

		price = SPApiDll.INSTANCE.SPAPI_SubscribePrice(userid, product, 0);

		sleep(1000);

		logout = SPApiDll.INSTANCE.SPAPI_Logout(userid);

		while (logout != 0)
		{
			sleep(1000);

			System.out.println("Logout: " + logout);
		}

		System.out.println("init: " + in);
		System.out.println("login: " + login);

		System.out.println("logout: " + logout);

		un = SPApiDll.INSTANCE.SPAPI_Uninitialize(userid);

		// System.out.println("init: " + in);
		// System.out.println("login: " + login);
		//
		// System.out.println("logout: " + logout);
		System.out.println("uninit: " + un);

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
