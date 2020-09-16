package com.msg.model;

import java.util.*;

public interface MsgDAO_interface {
          public void insert(MsgVO MsgVO);
          public void update(MsgVO MsgVO);
          public void delete(String Msgid);
          public MsgVO findByPrimaryKey(String Textid);
          public MsgVO findByPrimaryKey1(String text_id);
          public List<MsgVO> getAll();
          //�U�νƦX�d��(�ǤJ�Ѽƫ��AMap)(�^�� List)
//        public List<EmpVO> getAll(Map<String, String[]> map); 
//		public Set<MsgVO> getMsgsByText_id(String text_id);
}
