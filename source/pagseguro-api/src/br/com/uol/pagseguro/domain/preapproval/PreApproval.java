/*
 ************************************************************************
 Copyright [2014] [PagSeguro Internet Ltda.]

 Licensed under the Apache License, Version 2.0 (the "License");
 you may not use this file except in compliance with the License.
 You may obtain a copy of the License at

 http://www.apache.org/licenses/LICENSE-2.0

 Unless required by applicable law or agreed to in writing, software
 distributed under the License is distributed on an "AS IS" BASIS,
 WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 See the License for the specific language governing permissions and
 limitations under the License.
 ************************************************************************
 */

package br.com.uol.pagseguro.domain.preapproval;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import br.com.uol.pagseguro.enums.DayOfWeek;
import br.com.uol.pagseguro.enums.PreApprovalManagementType;
import br.com.uol.pagseguro.enums.PreApprovalPeriod;

/**
 *
 * Represents a pre-approval transaction
 *
 */
public class PreApproval {

    /**
     * Management type
     *
     * Optional
     */
    private PreApprovalManagementType managementType;

    /**
     * Pre-approval name
     */
    private String name;

    /**
     * Pre-approval details
     *
     * Optional
     */
    private String details;

    /**
     * Amount of each payment
     *
     * Optional
     */
    private BigDecimal amountPerPayment;

    /**
     * Maximum amount of each payment
     *
     * Optional
     */
    private BigDecimal maxAmountPerPayment;

    /**
     * Periodicity of a pre-approval
     */
    private PreApprovalPeriod period;

    /**
     * Week day in which the charging will be sent
     *
     * Optional
     */
    private DayOfWeek dayOfWeek;

    /**
     * Day of month in which the charging will be sent
     *
     * Optional
     */
    private Integer dayOfMonth;

    /**
     * Day of year in which the charging will be sent
     *
     * Optional
     */
    private Date dayOfYear;

    /**
     * Maximum number of payments per period
     * 
     * Optional
     */
    private Integer maxPaymentsPerPeriod;

    /**
     * Maximum charging amount per period
     */
    private BigDecimal maxAmountPerPeriod;

    /**
     * Initial date of the pre-approval
     *
     * Optional
     */
    private String initialDate;

    /**
     * Final date of the pre-approval
     */
    private String finalDate;

    /**
     * Maximum charging amount per payment
     */
    private BigDecimal maxTotalAmount;

    /**
     * URL in which the sender will be redirected to, in case he/she requests the cancellation of the pre-approval
     */
    private String cancelURL;

    /**
     * Initializes a new instance of the PreApproval class with the specified arguments
     */
    public PreApproval() {

    }

    /**
     * Initializes a new instance of the PreApproval class with the specified arguments
     *
     * @param name
     * @param period
     * @param maxAmountPerPeriod
     * @param finalDate
     * @param cancelURL
     */
    public PreApproval(final String name, final PreApprovalPeriod period, final BigDecimal maxAmountPerPeriod,
            final Calendar finalDate, final String cancelURL) {

        this.name = name;
        this.period = period;
        this.maxAmountPerPeriod = maxAmountPerPeriod;

        final XMLGregorianCalendar xmlGregorianCalendar;
        try {
            GregorianCalendar gregorianCalendar = new GregorianCalendar();
            gregorianCalendar.setTime(finalDate.getTime());
            xmlGregorianCalendar = DatatypeFactory.newInstance().newXMLGregorianCalendar(gregorianCalendar);
            this.finalDate = xmlGregorianCalendar.toString();
        } catch (DatatypeConfigurationException e) {
            this.finalDate = null;
        }

        this.cancelURL = cancelURL;
    }

    /**
     * Initializes a new instance of the PreApproval class with the specified arguments
     *
     * @param managementType
     * @param name
     * @param details
     * @param amountPerPayment
     * @param maxAmountPerPayment
     * @param period
     * @param dayOfWeek
     * @param dayOfMonth
     * @param dayOfYear
     * @param maxPaymentsPerPeriod
     * @param maxAmountPerPeriod
     * @param initialDate
     * @param finalDate
     * @param maxTotalAmount
     * @param cancelURL
     */
    public PreApproval(final PreApprovalManagementType managementType, final String name, final String details,
            final BigDecimal amountPerPayment, final BigDecimal maxAmountPerPayment, final PreApprovalPeriod period,
            final DayOfWeek dayOfWeek, final Integer dayOfMonth, final Date dayOfYear,
            final Integer maxPaymentsPerPeriod, final BigDecimal maxAmountPerPeriod, final Date initialDate,
            final Date finalDate, final BigDecimal maxTotalAmount, final String cancelURL) {

        this.managementType = managementType;
        this.name = name;
        this.details = details;
        this.amountPerPayment = amountPerPayment;
        this.maxAmountPerPayment = maxAmountPerPayment;
        this.period = period;
        this.dayOfWeek = dayOfWeek;
        this.dayOfMonth = dayOfMonth;
        this.dayOfYear = dayOfYear;
        this.maxPaymentsPerPeriod = maxPaymentsPerPeriod;
        this.maxAmountPerPeriod = maxAmountPerPeriod;

        SimpleDateFormat sDF = new SimpleDateFormat("yyyy-MM-dd");
        this.initialDate = sDF.format(initialDate);
        this.finalDate = sDF.format(finalDate);

        this.maxTotalAmount = maxTotalAmount;
        this.cancelURL = cancelURL;
    }

    /**
     * @return managementType
     */
    public PreApprovalManagementType getManagementType() {

        return managementType;
    }

    /**
     * Sets managementType
     *
     * @param managementType
     */
    public void setManagementType(final PreApprovalManagementType managementType) {

        this.managementType = managementType;
    }

    /**
     * @return name
     */
    public String getName() {

        return name;
    }

    /**
     * Sets name
     *
     * @param name
     */
    public void setName(final String name) {

        this.name = name;
    }

    /**
     * @return details
     */
    public String getDetails() {

        return details;
    }

    /**
     * Sets details
     *
     * @param details
     */
    public void setDetails(final String details) {

        this.details = details;
    }

    /**
     * @return amountPerPayment
     */
    public BigDecimal getAmountPerPayment() {

        return amountPerPayment;
    }

    /**
     * Sets amountPerPayment
     *
     * @param amountPerPayment
     */
    public void setAmountPerPayment(final BigDecimal amountPerPayment) {

        this.amountPerPayment = amountPerPayment;
    }

    /**
     * @return maxAmountPerPayment
     */
    public BigDecimal getMaxAmountPerPayment() {

        return maxAmountPerPayment;
    }

    /**
     * Sets maxAmountPerPayment
     *
     * @param maxAmountPerPayment
     */
    public void setMaxAmountPerPayment(final BigDecimal maxAmountPerPayment) {

        this.maxAmountPerPayment = maxAmountPerPayment;
    }

    /**
     * @return period
     */
    public PreApprovalPeriod getPeriod() {

        return period;
    }

    /**
     * Sets period
     *
     * @param period
     */
    public void setPeriod(final PreApprovalPeriod period) {

        this.period = period;
    }

    /**
     * @return dayOfWeek
     */
    public DayOfWeek getDayOfWeek() {

        return dayOfWeek;
    }

    /**
     * Sets dayOfWeek
     *
     * @param dayOfWeek
     */
    public void setDayOfWeek(final DayOfWeek dayOfWeek) {

        this.dayOfWeek = dayOfWeek;
    }

    /**
     * @return dayOfMonth
     */
    public Integer getDayOfMonth() {

        return dayOfMonth;
    }

    /**
     * Sets dayOfMonth
     *
     * @param dayOfMonth
     */
    public void setDayOfMonth(final Integer dayOfMonth) {

        this.dayOfMonth = dayOfMonth;
    }

    /**
     * @return dayOfYear
     */
    public Date getDayOfYear() {

        return dayOfYear;
    }

    /**
     * Sets dayOfYear
     *
     * @param dayOfYear
     */
    public void setDayOfYear(final Date dayOfYear) {

        this.dayOfYear = dayOfYear;
    }

    /**
     * @return maxPaymentsPerPeriod
     */
    public Integer getMaxPaymentsPerPeriod() {

        return maxPaymentsPerPeriod;
    }

    /**
     * Sets maxPaymentsPerPeriod
     *
     * @param maxPaymentsPerPeriod
     */
    public void setMaxPaymentsPerPeriod(final Integer maxPaymentsPerPeriod) {

        this.maxPaymentsPerPeriod = maxPaymentsPerPeriod;
    }

    /**
     * @return maxAmountPerPeriod
     */
    public BigDecimal getMaxAmountPerPeriod() {

        return maxAmountPerPeriod;
    }

    /**
     * Sets maxAmountPerPeriod
     *
     * @param maxAmountPerPeriod
     */
    public void setMaxAmountPerPeriod(final BigDecimal maxAmountPerPeriod) {

        this.maxAmountPerPeriod = maxAmountPerPeriod;
    }

    /**
     * @return initialDate
     */
    public String getInitialDate() {

        return initialDate;
    }

    /**
     * Sets initialDate
     *
     * @param initialDate
     */
    public void setInitialDate(final Date initialDate) {
        SimpleDateFormat sDF = new SimpleDateFormat("yyyy-MM-dd");
        this.initialDate = sDF.format(initialDate);
    }

    /**
     * @return finalDate
     */
    public String getFinalDate() {

        return finalDate;
    }

    /**
     * Sets finalDate
     *
     * @param finalDate
     */
    public void setFinalDate(final Date finalDate) {
        SimpleDateFormat sDF = new SimpleDateFormat("yyyy-MM-dd");
        this.finalDate = sDF.format(finalDate);
    }

    /**
     * @return maxTotalAmount
     */
    public BigDecimal getMaxTotalAmount() {

        return maxTotalAmount;
    }

    /**
     * Sets maxTotalAmount
     *
     * @param maxTotalAmount
     */
    public void setMaxTotalAmount(final BigDecimal maxTotalAmount) {

        this.maxTotalAmount = maxTotalAmount;
    }

    /**
     * @return cancelURL
     */
    public String getCancelURL() {

        return cancelURL;
    }

    /**
     * Sets cancelURL
     *
     * @param cancelURL
     */
    public void setCancelURL(final String cancelURL) {

        this.cancelURL = cancelURL;
    }

    /**
     * @return string
     */
    @Override
    public String toString() {

        final StringBuilder builder = new StringBuilder()//
                .append("PreApproval [")//
                .append("managementType=")//
                .append(managementType)//
                .append(",name=\"")//
                .append(name + "\"")//
                .append(",details=\"")//
                .append(details + "\"")//
                .append(",amountPerPayment=")//
                .append(amountPerPayment)//
                .append(",maxAmountPerPayment=")//
                .append(maxAmountPerPayment)//
                .append(",period=")//
                .append(period)//
                .append(",dayOfWeek=")//
                .append(dayOfWeek)//
                .append(",dayOfMonth=")//
                .append(dayOfMonth)//
                .append(",dayOfYear=")//
                .append(dayOfYear)//
                .append(",maxPaymentsPerPeriod=")//
                .append(maxPaymentsPerPeriod)//
                .append(",maxAmountPerPeriod=")//
                .append(maxAmountPerPeriod)//
                .append(",initialDate=\"")//
                .append(initialDate + "\"")//
                .append(",finalDate=\"")//
                .append(finalDate + "\"")//
                .append(",maxTotalAmount=")//
                .append(maxTotalAmount)//
                .append(",cancelURL=\"")//
                .append(cancelURL + "\"")//
                .append("]");
        return builder.toString();
    }

}
