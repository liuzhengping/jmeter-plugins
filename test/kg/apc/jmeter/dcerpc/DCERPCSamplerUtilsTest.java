package kg.apc.jmeter.dcerpc;

import org.apache.jorphan.util.JOrphanUtils;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author APC
 */
public class DCERPCSamplerUtilsTest
{
   private static String SERVER_UUID = "80d7862a-6160-4596-aaa9-1743e4c27638";
   private static String ABSTRACT_SYNTAX = "8a885d04-1ceb-11c9-9fe8-08002b104860";

   public DCERPCSamplerUtilsTest()
   {
   }

   @BeforeClass
   public static void setUpClass()
        throws Exception
   {
   }

   @AfterClass
   public static void tearDownClass()
        throws Exception
   {
   }

   @Before
   public void setUp()
   {
   }

   @After
   public void tearDown()
   {
   }

   /**
    * Test of getRequestByString method, of class DCERPCSampler.
    */
   @Test
   public void testGetRequestByString()
   {
      System.out.println("getRequestByString");
      String str = "bind\n"
           + SERVER_UUID + "\n"
           + ABSTRACT_SYNTAX;

      String expResult = "05000b03100000004800000001000000d016d0160000000001000000000001002a86d78060619645aaa91743e4c2763801000000045d888aeb1cc9119fe808002b10486002000000";
      RPCPacket[] result = DCERPCSamplerUtils.getRequestsArrayByString(str);
      assertEquals(1, result.length);
      assertEquals(expResult, JOrphanUtils.baToHexString(result[0].getBytes()));
   }

   @Test
   public void testGetRequestByString2()
   {
      System.out.println("getRequestByString2");
      String str = "1\n"
           + "0\n"
           + "00000000";

      String expResult = "05000003100000001c00000001000000040000000000000000000000";
      RPCPacket[] result = DCERPCSamplerUtils.getRequestsArrayByString(str);
      assertEquals(1, result.length);
      assertEquals(expResult, JOrphanUtils.baToHexString(result[0].getBytes()));
   }

   @Test
   public void testGetRequestByString3()
   {
      System.out.println("getRequestByString3");
      String str = "1\n"
           + "0\n"
           + "00{TEST}\r\n0000";

      String expResult = "05000003100000001f00000001000000070000000000000000544553540000";
      RPCPacket[] result = DCERPCSamplerUtils.getRequestsArrayByString(str);
      assertEquals(1, result.length);
      assertEquals(expResult, JOrphanUtils.baToHexString(result[0].getBytes()));
   }

   @Test
   public void testGetRequestByString_large1()
   {
      System.out.println("getRequestByString_large");
      String large = "ff";
      for (int n = 0; n < 15; n++)
         large += large;

      RPCPacket[] result = DCERPCSamplerUtils.getRequestsArrayByString("1\n1\n" + large);
      assertEquals(6, result.length);
   }

   @Test
   public void testGetRequestByString_large2()
   {
      System.out.println("getRequestByString_large");
      final int len = RPCBindRequest.maxXmitFrag - RPCCallRequest.HEADER_LENGTH;

      // border condition 0
      String large = JOrphanUtils.baToHexString(new byte[len]);

      RPCPacket[] result = DCERPCSamplerUtils.getRequestsArrayByString("1\n1\n" + large);
      assertEquals(1, result.length);
   }

   @Test
   public void testGetRequestByString_large3()
   {
      System.out.println("getRequestByString_large");
      final int len = RPCBindRequest.maxXmitFrag - RPCCallRequest.HEADER_LENGTH + 1;

      // border condition +1
      String large = JOrphanUtils.baToHexString(new byte[len]);

      RPCPacket[] result = DCERPCSamplerUtils.getRequestsArrayByString("1\n1\n" + large);
      assertEquals(2, result.length);
   }

   public void testGetRequestByString_large4()
   {
      System.out.println("getRequestByString_large");
      final int len = RPCBindRequest.maxXmitFrag - RPCCallRequest.HEADER_LENGTH - 1;

      // border condition -1
      String large = JOrphanUtils.baToHexString(new byte[len]);

      RPCPacket[] result = DCERPCSamplerUtils.getRequestsArrayByString("1\n1\n" + large);
      assertEquals(1, result.length);
   }
}