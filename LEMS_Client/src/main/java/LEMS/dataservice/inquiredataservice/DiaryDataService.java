package LEMS.dataservice.inquiredataservice;

import LEMS.po.inquirepo.DiaryPO;

/**
 * @author 章承尧
 * 日志数据层接口
 */
public interface DiaryDataService {
	public DiaryPO findDiary(String date);
}