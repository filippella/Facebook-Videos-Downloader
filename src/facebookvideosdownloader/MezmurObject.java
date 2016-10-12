/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facebookvideosdownloader;

/**
 *
 * @author Filippo
 */
public class MezmurObject {

    private Data[] data;
    private Paging paging;

    public Data[] getData() {
        return data;
    }

    public void setData(Data[] data) {
        this.data = data;
    }

    public Paging getPaging() {
        return paging;
    }

    public void setPaging(Paging paging) {
        this.paging = paging;
    }

    @Override
    public String toString() {
        return "MezmurObject [data = " + data + ", paging = " + paging + "]";
    }
}
