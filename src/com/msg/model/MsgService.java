package com.msg.model;

import java.sql.Timestamp;
import java.util.List;
import java.util.Set;

import com.text_main.model.TextMainVO;

public class MsgService {

	private MsgDAO_interface dao;

	public MsgService() {
		dao = new I_MsgDAO();
	}

	public MsgVO addMsg( String text_id,String author_id,
			String content) {

		MsgVO msgVO = new MsgVO();
//		msgVO.setMsg_id(msg_id);
		msgVO.setText_id(text_id);
		msgVO.setAuthor_id(author_id);
		msgVO.setContent(content);
//		msgVO.setStatus(status);
		dao.insert(msgVO);

		return msgVO;
	}

	public MsgVO updateMsg(String msg_id,String text_id, String author_id,
			String content,java.sql.Timestamp est_time ,Integer status) {

		MsgVO msgVO = new MsgVO();

		msgVO.setMsg_id(msg_id);
		msgVO.setText_id(text_id);
		msgVO.setAuthor_id(author_id);
		msgVO.setContent(content);
		msgVO.setEst_time(est_time);
		msgVO.setStatus(status);
		dao.update(msgVO);

		return msgVO;
	}

	public void deleteMsg(String msg_id) {
		dao.delete(msg_id);
	}

	public MsgVO getOneMsg(String msg_id) {
		return dao.findByPrimaryKey(msg_id);
	}
	public MsgVO getOneMsg1(String text_id) {
		return dao.findByPrimaryKey(text_id);
	}

	public List<MsgVO> getAll() {
		return dao.getAll();
	}
	
}
