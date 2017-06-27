package com.gb.pojo;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Item implements Serializable{
    private static final long serialVersionUID = -7311730619961846887L;
    private Integer id;

    private String title;

    private String sellPoint;

    private Integer price;

    private Integer num;

    private String barcode;

    private String image;

    private Integer cid;

    private Integer status;

    private Date created;

    private Date updated;

    private ItemCat cat;
    private String showCreated;

    private String showUpdated;




    public String getShowCreated() {
        if(this.created!=null){
            SimpleDateFormat sp=new SimpleDateFormat("yyyy年MM月dd日");
            return sp.format(this.created);
        }
        return "";
    }

    public void setShowCreated(String showCreated) {
        this.showCreated = showCreated;
    }

    public String getShowUpdated() {
        if(this.updated!=null){
            SimpleDateFormat sp=new SimpleDateFormat("yyyy年MM月dd日 HH点mm分ss秒");
            return sp.format(this.updated);
        }
        return "";
    }

    public void setShowUpdated(String showUpdated) {
        this.showUpdated = showUpdated;
    }

    public ItemCat getCat() {
        return cat;
    }

    public void setCat(ItemCat cat) {
        this.cat = cat;
    }

    private String sort;

    private String order;

    private Integer start;

    private Integer end;

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public Integer getStart() {
        return start;
    }

    public void setStart(Integer start) {
        this.start = start;
    }

    public Integer getEnd() {
        return end;
    }

    public void setEnd(Integer end) {
        this.end = end;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getSellPoint() {
        return sellPoint;
    }

    public void setSellPoint(String sellPoint) {
        this.sellPoint = sellPoint == null ? null : sellPoint.trim();
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode == null ? null : barcode.trim();
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image == null ? null : image.trim();
    }

    public Integer getCid() {
        return cid;
    }

    public void setCid(Integer cid) {
        this.cid = cid;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getUpdated() {
        return updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }
}