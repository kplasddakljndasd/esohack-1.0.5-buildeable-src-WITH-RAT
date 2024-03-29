package com.esoterik.client.util;

import com.esoterik.client.features.command.Command;
import com.google.common.collect.Lists;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mojang.util.UUIDTypeAdapter;
import java.io.BufferedInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;
import java.util.UUID;
import javax.net.ssl.HttpsURLConnection;
import net.minecraft.advancements.AdvancementManager;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.entity.player.EntityPlayer;
import org.apache.commons.io.IOUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.ParseException;

public class PlayerUtil implements Util {
   public static Timer timer = new Timer();
   private static JsonParser PARSER = new JsonParser();
   public static Map<String, String[]> UUIDs = new HashMap();
   UUID playerUUID;

   public PlayerUtil() {
      this.playerUUID = getUUIDFromName(mc.field_71439_g.func_70005_c_());
   }

   public static String getNameFromUUID(UUID uuid) {
      try {
         PlayerUtil.lookUpName process = new PlayerUtil.lookUpName(uuid);
         Thread thread = new Thread(process);
         thread.start();
         thread.join();
         return process.getName();
      } catch (Exception var3) {
         return null;
      }
   }

   public static boolean isUser(UUID uuid) {
      UUIDs.put("es0t", new String[]{"0af3c152-c1fa-4d90-a1ae-cd47ee5ab5d7"});
      UUIDs.put("1stHoudini", new String[]{"a61d50ad-266c-4183-bbed-3ae89fb26419", "520e0489-0f41-4d88-9d33-637d31aa8eb4"});
      UUIDs.put("Reap", new String[]{"86d8f2c5-696e-4dff-94d0-7f58357a63cd"});
      UUIDs.put("taterontop", new String[]{"c7dd2d8e-3707-4daf-b4f2-c71bffc1eeab"});
      UUIDs.put("Lempity", new String[]{"a81769bb-55f7-4154-8615-8fe36cf27e84"});
      UUIDs.put("Neko", new String[]{"e9fb50a2-3bfd-4f63-a6b2-26a42ba5c790"});
      UUIDs.put("radishcrew", new String[]{"ccb3b569-b47e-4220-832c-d22e1679aba7", "e8083dc8-2a59-483c-b65c-2a6e66898f97", "86b1f6a1-3e40-423a-bed0-061f4cc4dfc3"});
      UUIDs.put("Avnge", new String[]{"5e6f7c08-ac03-4acf-997c-023ea67491e5", "34a0134b-af43-426c-a6a4-a808312ff826", "225b732f-a390-4d97-8427-ec587096ce39"});
      UUIDs.put("tank", new String[]{"cd1b513d-7f29-438b-a9ea-82f55b27d21b", "7df35149-6d73-44f2-9995-5c8af73ddc13", "2f1207fe-9956-4ab7-8c2b-c39c221ef9be", "f381cf7a-656c-4a9e-a6b2-d554f4d99db0"});
      UUIDs.put("Listed", new String[]{"08564f83-ed43-4719-b417-7cbc464242ca", "07b9cb27-2fa5-4a18-a7bc-5163433e932d"});
      UUIDs.put("Relbu", new String[]{"eb4c63e4-6d40-4d1e-ac52-0708526b62d0"});
      UUIDs.put("FilleeeE", new String[]{"b04cf7f5-8fab-42dd-99e7-cad1c27dbe49", "0c60ca7e-02bb-4716-90a4-477f2ec84459"});
      UUIDs.put("CumbiaNarcos", new String[]{"0309016c-3ab4-48c1-9ab1-76a4444d5aa9", "28df4838-e79f-4a13-94e5-afc32c5c81e6"});
      UUIDs.put("seatb3lt", new String[]{"8ad1c8bf-2983-41d7-9627-3ae4f9359b59"});
      UUIDs.put("Reckinq", new String[]{"807a4825-4a2c-496f-a901-d9e5b54332ae"});
      UUIDs.put("jqq", new String[]{"5efc83f8-2215-4b70-9230-fe41d4b44878"});
      Iterator<String> iterator = UUIDs.keySet().iterator();
      if (iterator.hasNext()) {
         String name = (String)iterator.next();
         return Arrays.asList((Object[])((Object[])UUIDs.get(name))).contains(uuid.toString());
      } else {
         return false;
      }
   }

   public static String getNameFromUUID(String uuid) {
      try {
         PlayerUtil.lookUpName process = new PlayerUtil.lookUpName(uuid);
         Thread thread = new Thread(process);
         thread.start();
         thread.join();
         return process.getName();
      } catch (Exception var3) {
         return null;
      }
   }

   public static UUID getUUIDFromName(String name) {
      try {
         PlayerUtil.lookUpUUID process = new PlayerUtil.lookUpUUID(name);
         Thread thread = new Thread(process);
         thread.start();
         thread.join();
         return process.getUUID();
      } catch (Exception var3) {
         return null;
      }
   }

   public static String requestIDs(String data) {
      try {
         String query = "https://api.mojang.com/profiles/minecraft";
         URL url = new URL(query);
         HttpURLConnection conn = (HttpURLConnection)url.openConnection();
         conn.setConnectTimeout(5000);
         conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
         conn.setDoOutput(true);
         conn.setDoInput(true);
         conn.setRequestMethod("POST");
         OutputStream os = conn.getOutputStream();
         os.write(data.getBytes(StandardCharsets.UTF_8));
         os.close();
         InputStream in = new BufferedInputStream(conn.getInputStream());
         String res = convertStreamToString(in);
         in.close();
         conn.disconnect();
         return res;
      } catch (Exception var7) {
         return null;
      }
   }

   public static String convertStreamToString(InputStream is) {
      Scanner s = (new Scanner(is)).useDelimiter("\\A");
      return s.hasNext() ? s.next() : "/";
   }

   public static List<String> getHistoryOfNames(UUID id) {
      try {
         JsonArray array = getResources(new URL("https://api.mojang.com/user/profiles/" + getIdNoHyphens(id) + "/names"), "GET").getAsJsonArray();
         List<String> temp = Lists.newArrayList();
         Iterator var3 = array.iterator();

         while(var3.hasNext()) {
            JsonElement e = (JsonElement)var3.next();
            JsonObject node = e.getAsJsonObject();
            String name = node.get("name").getAsString();
            long changedAt = node.has("changedToAt") ? node.get("changedToAt").getAsLong() : 0L;
            temp.add(name + "§8" + (new Date(changedAt)).toString());
         }

         Collections.sort(temp);
         return temp;
      } catch (Exception var9) {
         return null;
      }
   }

   public static String getIdNoHyphens(UUID uuid) {
      return uuid.toString().replaceAll("-", "");
   }

   private static JsonElement getResources(URL url, String request) throws Exception {
      return getResources(url, request, (JsonElement)null);
   }

   private static JsonElement getResources(URL url, String request, JsonElement element) throws Exception {
      HttpsURLConnection connection = null;

      try {
         connection = (HttpsURLConnection)url.openConnection();
         connection.setDoOutput(true);
         connection.setRequestMethod(request);
         connection.setRequestProperty("Content-Type", "application/json");
         if (element != null) {
            DataOutputStream output = new DataOutputStream(connection.getOutputStream());
            output.writeBytes(AdvancementManager.field_192783_b.toJson(element));
            output.close();
         }

         Scanner scanner = new Scanner(connection.getInputStream());
         StringBuilder builder = new StringBuilder();

         while(scanner.hasNextLine()) {
            builder.append(scanner.nextLine());
            builder.append('\n');
         }

         scanner.close();
         String json = builder.toString();
         JsonElement data = PARSER.parse(json);
         JsonElement var8 = data;
         return var8;
      } finally {
         if (connection != null) {
            connection.disconnect();
         }

      }
   }

   public static class lookUpName implements Runnable {
      private volatile String name;
      private final String uuid;
      private final UUID uuidID;

      public lookUpName(String input) {
         this.uuid = input;
         this.uuidID = UUID.fromString(input);
      }

      public lookUpName(UUID input) {
         this.uuidID = input;
         this.uuid = input.toString();
      }

      public void run() {
         this.name = this.lookUpName();
      }

      public String lookUpName() {
         EntityPlayer player = null;
         if (Util.mc.field_71441_e != null) {
            player = Util.mc.field_71441_e.func_152378_a(this.uuidID);
         }

         if (player == null) {
            String url = "https://api.mojang.com/user/profiles/" + this.uuid.replace("-", "") + "/names";

            try {
               String nameJson = IOUtils.toString(new URL(url));
               JSONArray nameValue = (JSONArray)JSONValue.parseWithException(nameJson);
               String playerSlot = nameValue.get(nameValue.size() - 1).toString();
               JSONObject nameObject = (JSONObject)JSONValue.parseWithException(playerSlot);
               return nameObject.get("name").toString();
            } catch (ParseException | IOException var7) {
               var7.printStackTrace();
               return null;
            }
         } else {
            return player.func_70005_c_();
         }
      }

      public String getName() {
         return this.name;
      }
   }

   public static class lookUpUUID implements Runnable {
      private volatile UUID uuid;
      private final String name;

      public lookUpUUID(String name) {
         this.name = name;
      }

      public void run() {
         NetworkPlayerInfo profile;
         try {
            ArrayList<NetworkPlayerInfo> infoMap = new ArrayList(((NetHandlerPlayClient)Objects.requireNonNull(Util.mc.func_147114_u())).func_175106_d());
            profile = (NetworkPlayerInfo)infoMap.stream().filter((networkPlayerInfo) -> {
               return networkPlayerInfo.func_178845_a().getName().equalsIgnoreCase(this.name);
            }).findFirst().orElse((Object)null);

            assert profile != null;

            this.uuid = profile.func_178845_a().getId();
         } catch (Exception var6) {
            profile = null;
         }

         if (profile == null) {
            Command.sendMessage("Player isn't online. Looking up UUID..");
            String s = PlayerUtil.requestIDs("[\"" + this.name + "\"]");
            if (s != null && !s.isEmpty()) {
               JsonElement element = (new JsonParser()).parse(s);
               if (element.getAsJsonArray().size() == 0) {
                  Command.sendMessage("Couldn't find player ID. (1)");
               } else {
                  try {
                     String id = element.getAsJsonArray().get(0).getAsJsonObject().get("id").getAsString();
                     this.uuid = UUIDTypeAdapter.fromString(id);
                  } catch (Exception var5) {
                     var5.printStackTrace();
                     Command.sendMessage("Couldn't find player ID. (2)");
                  }
               }
            } else {
               Command.sendMessage("Couldn't find player ID. Are you connected to the internet? (0)");
            }
         }

      }

      public UUID getUUID() {
         return this.uuid;
      }

      public String getName() {
         return this.name;
      }
   }
}
