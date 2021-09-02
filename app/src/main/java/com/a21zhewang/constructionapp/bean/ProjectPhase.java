package com.a21zhewang.constructionapp.bean;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import com.a21zhewang.constructionapp.utils.TimeUtils;

import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * Created by 10430 on 2018/6/8.
 */

public class ProjectPhase implements Parcelable , Comparable<ProjectPhase>{

    private String dicID;
    private String dicName;
    private int selfIndex;



    public int getSelfIndex() {
        return selfIndex;
    }

    public void setSelfIndex(int selfIndex) {
        this.selfIndex = selfIndex;
    }

    private String planStartDate;
    private String planEndDate;
    private String actuallyStartDate;
    private String actuallyEndDate;
    private String completionRate;
    private String recordDesc;
    private String laggingdays ;

    public String getDicID() {
        return dicID;
    }

    public void setDicID(String dicID) {
        this.dicID = dicID;
    }

    public String getDicName() {
        return dicName;
    }

    public void setDicName(String dicName) {
        this.dicName = dicName;
    }

    public String getPlanStartDate() {
        return planStartDate;
    }

    public void setPlanStartDate(String planStartDate) {
        this.planStartDate = planStartDate;
    }

    public String getPlanEndDate() {
        return planEndDate;
    }

    public void setPlanEndDate(String planEndDate) {
        this.planEndDate = planEndDate;
    }

    public String getActuallyStartDate() {
        return actuallyStartDate;
    }

    public void setActuallyStartDate(String actuallyStartDate) {
        this.actuallyStartDate = actuallyStartDate;
    }

    public String getActuallyEndDate() {
        return actuallyEndDate;
    }

    public void setActuallyEndDate(String actuallyEndDate) {
        this.actuallyEndDate = actuallyEndDate;
    }

    public String getCompletionRate() {
        return completionRate;
    }

    public void setCompletionRate(String completionRate) {
        this.completionRate = completionRate;
    }

    public String getRecordDesc() {
        return recordDesc;
    }

    public void setRecordDesc(String recordDesc) {
        this.recordDesc = recordDesc;
    }

    public String getLaggingdays() {
         laggingdays = TimeUtils.getFitTimeSpan(actuallyEndDate, planEndDate, new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()), 1).replace("天","");
        if (TimeUtils.lessThan(planEndDate,actuallyEndDate))laggingdays="-"+laggingdays;
        return laggingdays;
    }

    public void setLaggingdays(String laggingdays) {
        this.laggingdays = laggingdays;
    }

    public ProjectPhase() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.dicID);
        dest.writeString(this.dicName);
        dest.writeInt(this.selfIndex);
        dest.writeString(this.planStartDate);
        dest.writeString(this.planEndDate);
        dest.writeString(this.actuallyStartDate);
        dest.writeString(this.actuallyEndDate);
        dest.writeString(this.completionRate);
        dest.writeString(this.recordDesc);
        dest.writeString(this.laggingdays);
    }

    protected ProjectPhase(Parcel in) {
        this.dicID = in.readString();
        this.dicName = in.readString();
        this.selfIndex = in.readInt();
        this.planStartDate = in.readString();
        this.planEndDate = in.readString();
        this.actuallyStartDate = in.readString();
        this.actuallyEndDate = in.readString();
        this.completionRate = in.readString();
        this.recordDesc = in.readString();
        this.laggingdays = in.readString();
    }

    public static final Creator<ProjectPhase> CREATOR = new Creator<ProjectPhase>() {
        @Override
        public ProjectPhase createFromParcel(Parcel source) {
            return new ProjectPhase(source);
        }

        @Override
        public ProjectPhase[] newArray(int size) {
            return new ProjectPhase[size];
        }
    };

    /**
     * Compares this object with the specified object for order.  Returns a
     * negative integer, zero, or a positive integer as this object is less
     * than, equal to, or greater than the specified object.
     * <p>
     * <p>The implementor must ensure <tt>sgn(x.compareTo(y)) ==
     * -sgn(y.compareTo(x))</tt> for all <tt>x</tt> and <tt>y</tt>.  (This
     * implies that <tt>x.compareTo(y)</tt> must throw an exception iff
     * <tt>y.compareTo(x)</tt> throws an exception.)
     * <p>
     * <p>The implementor must also ensure that the relation is transitive:
     * <tt>(x.compareTo(y)&gt;0 &amp;&amp; y.compareTo(z)&gt;0)</tt> implies
     * <tt>x.compareTo(z)&gt;0</tt>.
     * <p>
     * <p>Finally, the implementor must ensure that <tt>x.compareTo(y)==0</tt>
     * implies that <tt>sgn(x.compareTo(z)) == sgn(y.compareTo(z))</tt>, for
     * all <tt>z</tt>.
     * <p>
     * <p>It is strongly recommended, but <i>not</i> strictly required that
     * <tt>(x.compareTo(y)==0) == (x.equals(y))</tt>.  Generally speaking, any
     * class that implements the <tt>Comparable</tt> interface and violates
     * this condition should clearly indicate this fact.  The recommended
     * language is "Note: this class has a natural ordering that is
     * inconsistent with equals."
     * <p>
     * <p>In the foregoing description, the notation
     * <tt>sgn(</tt><i>expression</i><tt>)</tt> designates the mathematical
     * <i>signum</i> function, which is defined to return one of <tt>-1</tt>,
     * <tt>0</tt>, or <tt>1</tt> according to whether the value of
     * <i>expression</i> is negative, zero or positive.
     *
     * @param o the object to be compared.
     * @return a negative integer, zero, or a positive integer as this object
     * is less than, equal to, or greater than the specified object.
     * @throws NullPointerException if the specified object is null
     * @throws ClassCastException   if the specified object's type prevents it
     *                              from being compared to this object.
     */
    @Override
    public int compareTo(@NonNull ProjectPhase o) {
        int i = this.getSelfIndex() - o.getSelfIndex();
        return i;

    }
}
