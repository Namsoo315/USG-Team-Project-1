package com.example.gateway.security.api;

public abstract class ApiList {

    private static final String[] blackList = {

    };
    private static final String[] ownerList = {
            "/api/member"
    };
    public static String[] getBlackList() {
        return blackList;
    }
    public static String[] getOwnerList() {
        return ownerList;
    }
}
