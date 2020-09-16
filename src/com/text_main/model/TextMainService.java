package com.text_main.model;

import java.sql.Timestamp;
import java.util.List;
import java.util.Set;

import com.c2cpro_main.model.C2cproMainVO;
import com.msg.model.MsgVO;
import com.text_main.model.TextMainVO;

public class TextMainService {

	private TextMainDAO_interface dao;

	public TextMainService() {
		dao = new I_TextMainDAO();
	}

	public TextMainVO addText_main(String author_id, String title,
			String content ,Integer status) {

		TextMainVO TextMainVO = new TextMainVO();

//		TextMainVO.setText_id(text_id);
		TextMainVO.setAuthor_id(author_id);
		TextMainVO.setTitle(title);
		TextMainVO.setContent(content);
		TextMainVO.setStatus(status);
		dao.insert(TextMainVO);

		return TextMainVO;
	}

	public TextMainVO updateText_main(String text_id,String author_id, String title,
			String content,java.sql.Timestamp est_time, Integer status) {

		TextMainVO TextMainVO= new TextMainVO();

		TextMainVO.setText_id(text_id);
		TextMainVO.setAuthor_id(author_id);
		TextMainVO.setTitle(title);
		TextMainVO.setContent(content);
		TextMainVO.setEst_time(est_time);
		TextMainVO.setStatus(status);
		dao.update(TextMainVO);

		return TextMainVO;
	}

	public void deleteText_main(String text_id) {
		dao.delete(text_id);
	}

	public TextMainVO getOneText_main(String text_id) {
		return dao.findByPrimaryKey(text_id);
	}

	public List<TextMainVO> getAll() {
		return dao.getAll();
	}

	public List<TextMainVO> findByTextId(String text_id) {
		return dao.findByTextId(text_id);
	}
	public Set<MsgVO> getMsgsByText_id(String text_id){
		return dao.getMsgsByText_id(text_id);
	}
	public TextMainVO update_textStatus(String text_id,Integer status) {
		TextMainVO textMainVO = new TextMainVO ();
		textMainVO.setText_id(text_id);
		textMainVO.setStatus(status);
		dao.update_status(textMainVO);
		return textMainVO;
	}

		
	
}
