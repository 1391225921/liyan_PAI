package com.xie.beans;

public class ParamBeans {
	private String id;
	private String url;
	private String params;
	private String exp_json;
	private int is_need_token;
	private int is_use;
	private String remarks;

	public String getUrl() {
		return url;
	}

	public String getParams() {
		return params;
	}

	public void setParams(String params) {
		this.params = params;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getExp_json() {
		return exp_json;
	}

	public void setExp_json(String exp_json) {
		this.exp_json = exp_json;
	}

	public int getIs_need_token() {
		return is_need_token;
	}

	public void setIs_need_token(int is_need_token) {
		this.is_need_token = is_need_token;
	}

	public int getIs_use() {
		return is_use;
	}

	public void setIs_use(int is_use) {
		this.is_use = is_use;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String toString() {
		return "id=" + id + ",url=" + url + ",params=" + params;
	}

}
