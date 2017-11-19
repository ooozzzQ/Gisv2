package action;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import model.Car;
import service.CarServiceInterface;
import service.SearchCarServiceInterface;
import util.GlobalConstant;
import util.PagesSource;
import action.GpsUtil;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class SearchCarAction extends ActionSupport {

	private static final long serialVersionUID = 1L;

	private HttpServletRequest request;
	private SearchCarServiceInterface searchCarServiceInterface;
	private PagesSource pageSource;
	private String username;
	private int carTotalCount;
	private CarServiceInterface CarServiceInterface;
	private int pageNum;

	List<Car> allCarList = new ArrayList<Car>();

	public int getPageNum() {
		return pageNum;
	}

	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}

	public CarServiceInterface getCarServiceInterface() {
		return CarServiceInterface;
	}

	public void setCarServiceInterface(CarServiceInterface carServiceInterface) {
		CarServiceInterface = carServiceInterface;
	}

	public int getCarTotalCount() {
		return carTotalCount;
	}

	public void setCarTotalCount(int carTotalCount) {
		this.carTotalCount = carTotalCount;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public List<Car> getAllCarList() {
		return allCarList;
	}

	public void setAllCarList(List<Car> allCarList) {
		this.allCarList = allCarList;
	}

	public HttpServletRequest getRequest() {
		return request;
	}

	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}

	public SearchCarServiceInterface getSearchCarServiceInterface() {
		return searchCarServiceInterface;
	}

	public void setSearchCarServiceInterface(SearchCarServiceInterface searchCarServiceInterface) {
		this.searchCarServiceInterface = searchCarServiceInterface;
	}

	public PagesSource getPageSource() {
		return pageSource;
	}

	public void setPageSource(PagesSource pageSource) {
		this.pageSource = pageSource;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public SearchCarAction() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getCarList() {

		try {
			// �û����
			this.request = ServletActionContext.getRequest();
			username = (String) request.getSession().getAttribute(username);
			request.setAttribute("username", username);

			pageSource = new PagesSource(1,GlobalConstant.NUMBERPAGE);
			if (pageNum != 0 ) {
				pageSource.setNowPage(pageNum);
			}
			int carTotalCount = searchCarServiceInterface.getCarTotalCount();
			pageSource.setTotalCount(carTotalCount);
			allCarList = searchCarServiceInterface.getCarByPage(pageSource);
			ActionContext.getContext().put("username", username);
			ActionContext.getContext().put("carTotalCount", carTotalCount);
			ActionContext.getContext().put("page", pageSource);
			ActionContext.getContext().put("allCarList", allCarList);
			
			return "allCarList";
		} catch (Exception e) {
			e.printStackTrace();
			String msg = "获取车辆列表失败";
			ActionContext.getContext().put("msg", msg);
			return "error";
		}

	}


}
