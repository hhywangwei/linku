package com.tuoshecx.server.wx.small.event;

import java.util.Map;

class NoneSmallEventHandler extends SmallBaseEventHandler {

     @Override
     protected boolean isHandler(Map<String, String> data) {
         return true;
     }

     @Override
     protected String doHandler(String appid, Map<String, String> data) {
         return "success";
     }
 }
