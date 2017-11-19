package util;

import java.util.ArrayList;
import java.util.List;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 * ��ҳ�� ��Ҫ�Լ��ֶ�����
 * 
 * @nowPage ����ҳ setNowPage ;
 * @numberPage ÿҳ��¼�� setNumberPage() ;
 * @totalCount �ܼ�¼�� setTotalCount() ;
 * @hql ��ѯ��� setHql();(�� �ܼ�¼�� ����ѡһ��������) 2017/4/01
 */
public class PagesSource extends HibernateDaoSupport {
	/**
	 * ����ҳ
	 */
	private int nowPage;
	/**
	 * ÿҳ��¼��
	 */
	private int numberPage;
	/**
	 * �ܼ�¼��
	 */
	private int totalCount;
	/**
	 * ��ҳ��
	 */
	private int totalPage;
	/**
	 * ��ʼ��¼
	 */
	private int start;
	/*
	 * �����¼
	 */
	private int end;
	/*
	 * ��ѯ���
	 */
	private String hql;

	 
	public PagesSource() {
	}
 
	public PagesSource(int nowPage, int numberPage, int totalCount) {
		this.nowPage = nowPage;
		this.numberPage = numberPage;
		this.totalCount = totalCount;
	}

	public PagesSource(int nowPage, int numberPage) {
		this.nowPage = nowPage;
		this.numberPage = numberPage;
	}
	 
	public int findTotalRecords(String hql) {
		int records;
		try {
			records = new Integer(this.getHibernateTemplate().find(hql).get(0).toString());
		} catch (Exception e) {
			System.out.println("page error!");
			records = -1;
		}

		return records;
	}

	 
	public int getNowPage() {
		return nowPage;
	}

	public void setNowPage(int nowPage) {
		this.nowPage = nowPage;
	}

	 
	public int getNumberPage() {
		return numberPage;
	}

	public void setNumberPage(int numberPage) {
		this.numberPage = numberPage;
	}

	 
	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}
 
	public int getTotalPage() {
		if (totalCount % numberPage == 0) {
			totalPage = totalCount / numberPage;
		} else {
			totalPage = totalCount / numberPage + 1;
		}
		return totalPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	 
	public int getStart() {
		start = (nowPage - 1) * numberPage;
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	 
	public int getEnd() {
		end = numberPage * nowPage;
		if (end > totalCount) {
			end = totalCount;
		}
		return end;
	}

	public void setEnd(int end) {
		this.end = end;
	}

	 
	public String getHql() {
		return hql;
	}

	public void setHql(String hql) {
		try {
			this.totalCount = this.findTotalRecords(hql);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("PagesSource setHql error !");
		}
		this.hql = hql;
	}
 
	public List<Integer> getPageList() {
		int tPage = this.getTotalPage();
		List<Integer> pL = new ArrayList<Integer>();
		for (int i = 0; i < tPage; i++) {
			pL.add(i + 1);
		}
		return pL;
	}

	 
	public String turnHql(String hql) {
		System.out.println(hql);
		String hqlApply = hql.toLowerCase();
		int i = hqlApply.indexOf("select ");
		int j = hqlApply.indexOf("from ");
		int k = hqlApply.indexOf(" count");
		if (k == -1) {
			if (i != -1 && j != -1) {
				String middle = hql.substring(i + 6, j).trim();
				if ("".equals(middle))
					hql = "select count(*) " + hql.substring(j, hql.length());
				else
					hql = "select count(" + middle + ") " + hql.substring(j, hql.length());
			} else if (j != -1) {
				hql = "select count(*) " + hql.substring(j, hql.length());
			}
		}

		System.out.println("turn to: " + hql);
		return hql;
	}

}