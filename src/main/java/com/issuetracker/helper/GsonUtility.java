package com.issuetracker.helper;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class GsonUtility {
	static String className = "com.googleauth.demo.GsonUtility";
	static Gson gson = new Gson();

	public static String tojson(Object object) {
		return gson.toJson(object);
	}

	public static String getFbAccessTokenFromJson(String j) {
		JsonObject json = (JsonObject) new JsonParser().parse(j);
		JsonObject authr = (JsonObject) json.get("authResponse");
		String act = authr.get("access_token").getAsString();
		return act;
	}

	public static String getJsonElementString(String name, String gs) {
		try {
			JsonObject json = (JsonObject) new JsonParser().parse(gs);
			return json.get(name).getAsString();
		} catch (Exception localException) {
		}
		return null;
	}

	public static void main(String[] args) {
//		String s = getJsonElementString("access_token",
//				"{  \"access_token\" : \"ya29.AHES6ZQOuY3Li8MxhD2GOm9E_kaykd0zGAA9x697G7sdHQm5dmwbtA\",  \"token_type\" : \"Bearer\",  \"expires_in\" : 3600,  \"id_token\" : \"eyJhbGciOiJSUzI1NiIsImtpZCI6ImY0MTg2NmE0MTJmZmE5YTFhMDNjYzY5YjY1NDVmZDYzYzM1OGQ0ZDQifQ.eyJpc3MiOiJhY2NvdW50cy5nb29nbGUuY29tIiwiY2lkIjoiNzMwNjc5MDA1NjcyLmFwcHMuZ29vZ2xldXNlcmNvbnRlbnQuY29tIiwiYXpwIjoiNzMwNjc5MDA1NjcyLmFwcHMuZ29vZ2xldXNlcmNvbnRlbnQuY29tIiwiZW1haWwiOiJhZG1pbkBzb2RoYW5hbGlicmFyeS5jb20iLCJ0b2tlbl9oYXNoIjoieDJfbGlLVjdDNm9halJKMF9aQVlFdyIsImF0X2hhc2giOiJ4Ml9saUtWN0M2b2FqUkowX1pBWUV3IiwiYXVkIjoiNzMwNjc5MDA1NjcyLmFwcHMuZ29vZ2xldXNlcmNvbnRlbnQuY29tIiwiaGQiOiJzb2RoYW5hbGlicmFyeS5jb20iLCJ2ZXJpZmllZF9lbWFpbCI6InRydWUiLCJlbWFpbF92ZXJpZmllZCI6InRydWUiLCJpZCI6IjExNDcyOTc2Nzk4Njk3OTU5Nzk1NCIsInN1YiI6IjExNDcyOTc2Nzk4Njk3OTU5Nzk1NCIsImlhdCI6MTM3MDYyNjk1NSwiZXhwIjoxMzcwNjMwODU1fQ.gYBqxfWXmpKIzQDP7AGxGipih5vga1oChKg5YC5OVxwFPpGiXWCwjE46hFq37g0_vdsD93W5lbVPWTr78NqHtUbpQOwOpddATPtjTQt9uXIVG8HrxZwji_5oPYuPpmhFYBfPSLX-08ee9KPfVX3ikn6wTCfe7aR5n-T_Z8-K1IU\"}");
//		System.out.println(s);
	}

	public static String getElementString(String string, String line1) {
		if (line1.indexOf(string) != -1) {
			int k = string.length();
			return line1.substring(k + 1, line1.indexOf("&"));
		}
		return line1;
	}
}
