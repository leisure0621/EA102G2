package com.text_rep.model;

import java.util.*;

public interface TextRepDAO_interface {
          public void insert(TextRepVO MsgVO);
          public void update(TextRepVO TextRepVO);
          public void delete(String Msgid);
          public TextRepVO findByPrimaryKey(String rep_id);
          public List<TextRepVO> getAll();
          //�U�νƦX�d��(�ǤJ�Ѽƫ��AMap)(�^�� List)
//        public List<EmpVO> getAll(Map<String, String[]> map); 
}
