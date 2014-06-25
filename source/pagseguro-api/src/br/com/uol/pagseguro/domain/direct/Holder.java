package br.com.uol.pagseguro.domain.direct;

import br.com.uol.pagseguro.domain.Document;
import br.com.uol.pagseguro.domain.Phone;
import br.com.uol.pagseguro.helper.PagSeguroUtil;

/**
 * Represents the holder of the Credit Card
 */
public class Holder {

    /** Holder Name */
    private String name;

    /** Holder Phone */
    private Phone phone;

    /** Holder Document */
    private Document document;

    /** Holder Birth Date */
    private String birthDate;

    /**
     * Initializes a new instance of the Holder class
     */
    public Holder() {
    }

    /**
     * Initializes a new instance of the Holder class
     * 
     * @param name
     * @param phone
     * @param document
     * @param birthDate
     */
    public Holder(String name, Phone phone, Document document, String birthDate) {
        this.name = PagSeguroUtil.removeExtraSpaces(name);
        this.phone = phone;
        this.document = document;
        this.birthDate = birthDate;
    }

    /**
     * @return the holder name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name
     *            the holder name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the holder phone
     */
    public Phone getPhone() {
        return phone;
    }

    /**
     * @param phone
     *            the holder phone to set
     */
    public void setPhone(Phone phone) {
        this.phone = phone;
    }

    /**
     * @return the holder document
     */
    public Document getDocument() {
        return document;
    }

    /**
     * @param document
     *            the holder document to set
     */
    public void setDocument(Document document) {
        this.document = document;
    }

    /**
     * @return the holder birth date
     */
    public String getBirthDate() {
        return birthDate;
    }

    /**
     * @param birthDate
     *            the holder birth Date to set
     */
    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    @Override
    public String toString() {
        return "Holder [name=" + name + ", document=" + document + ", birthDate=" + birthDate + ", phone=" + phone
                + "]";
    }

}
