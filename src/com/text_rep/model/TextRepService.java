package com.text_rep.model;

import java.sql.Timestamp;
import java.util.List;
import com.text_rep.model.TextRepVO;
public class TextRepService {

	private TextRepDAO_interface dao;

	public TextRepService() {
		dao = new TextRepDAO();
	}

	public TextRepVO addtextRep(String text_id,String informant,
			 String case_description ) {

		TextRepVO TextRepVO = new TextRepVO();
		TextRepVO.setText_id(text_id);
		TextRepVO.setInformant(informant);
		TextRepVO.setCase_description(case_description);
		dao.insert(TextRepVO);

		return TextRepVO;
	}

	public TextRepVO update_textrepProcess(String rep_id,
			  Integer process) {

		TextRepVO TextRepVO = new TextRepVO();

		TextRepVO.setRep_id(rep_id);
	//	TextRepVO.setInformant(informant);
	//	TextRepVO.setText_id(text_id);
	//	TextRepVO.setCase_description(case_description);
		TextRepVO.setProcess(process);
		dao.update(TextRepVO);

		return TextRepVO;
	}

	public void deleteText_main(String msg_id) {
		dao.delete(msg_id);
	}

	public TextRepVO getOneText_main(String text_id) {
		return dao.findByPrimaryKey(text_id);
	}
	public TextRepVO getOneRep(String rep_id) {
		return dao.findByPrimaryKey(rep_id);
	}
	public List<TextRepVO> getAll() {
		return dao.getAll();
	}
}
