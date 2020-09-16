package com.text_main.model;

import java.util.*;

import com.msg.model.MsgVO;

public interface TextMainDAO_interface {
          public void insert(TextMainVO TextIdVO);
          public void update(TextMainVO TextIdVO);
          public void update_status(TextMainVO TextMainVO);
          public void delete(String TextId);
          public TextMainVO findByPrimaryKey(String text_id);
          public List<TextMainVO> getAll();
          public List<TextMainVO> findByTextId(String text_id);
		public Set<MsgVO> getMsgsByText_id(String text_id);
}
