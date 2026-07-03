/*
 * package com.happytohelp.homeloan.service;
 * 
 * 
 * 
 * import com.happytohelp.homeloan.model.LoanApplication; import
 * com.happytohelp.homeloan.model.User; import com.lowagie.text.*; import
 * com.lowagie.text.pdf.PdfWriter; import
 * org.springframework.beans.factory.annotation.Value; import
 * org.springframework.stereotype.Service;
 * 
 * import java.io.ByteArrayOutputStream;
 * 
 * @Service public class PdfLetterService {
 * 
 * @Value("${app.bank.name}") private String bankName;
 * 
 * @Value("${app.bank.branch}") private String bankBranch;
 * 
 * @Value("${app.bank.contact}") private String bankContact;
 * 
 * public byte[] buildSanctionLetter(LoanApplication app, User customer) {
 * ByteArrayOutputStream baos = new ByteArrayOutputStream(); Document doc = new
 * Document(PageSize.A4, 48, 48, 48, 48);
 * 
 * try { PdfWriter.getInstance(doc, baos); doc.open();
 * 
 * Font title = new Font(Font.HELVETICA, 16, Font.BOLD); Font header = new
 * Font(Font.HELVETICA, 14, Font.BOLD); Font normal = new Font(Font.HELVETICA,
 * 11);
 * 
 * doc.add(new Paragraph(bankName, title)); doc.add(new Paragraph(bankBranch +
 * " | Contact: " + bankContact, normal)); doc.add(Chunk.NEWLINE);
 * 
 * doc.add(new Paragraph("SANCTION LETTER", header)); doc.add(Chunk.NEWLINE);
 * 
 * doc.add(new Paragraph("Date: " + app.getSanctionedDate(), normal));
 * doc.add(new Paragraph("Applicant: " + customer.getName(), normal));
 * doc.add(new Paragraph("Email: " + customer.getEmail(), normal)); doc.add(new
 * Paragraph("Application ID: " + app.getId(), normal)); doc.add(Chunk.NEWLINE);
 * 
 * doc.add(new Paragraph("Sanctioned Amount: " + app.getSanctionedAmount(),
 * normal)); doc.add(new Paragraph("Interest Rate (Annual): " +
 * app.getAnnualInterestRate() + " %", normal)); doc.add(new
 * Paragraph("Tenure: " + app.getTenureMonths() + " months", normal));
 * doc.add(Chunk.NEWLINE);
 * 
 * doc.add(new Paragraph("Property Address: " + app.getPropertyAddress(),
 * normal)); doc.add(new Paragraph("Remarks/Conditions: " +
 * (app.getAdminRemarks() == null ? "-" : app.getAdminRemarks()), normal));
 * doc.add(Chunk.NEWLINE);
 * 
 * doc.add(new Paragraph("Authorized Signatory", normal)); doc.add(new
 * Paragraph(bankName, normal));
 * 
 * doc.close(); return baos.toByteArray(); } catch (Exception e) { throw new
 * RuntimeException("Failed to create sanction PDF: " + e.getMessage(), e); } }
 * 
 * public byte[] buildDisbursementLetter(LoanApplication app, User customer) {
 * ByteArrayOutputStream baos = new ByteArrayOutputStream(); Document doc = new
 * Document(PageSize.A4, 48, 48, 48, 48);
 * 
 * try { PdfWriter.getInstance(doc, baos); doc.open();
 * 
 * Font title = new Font(Font.HELVETICA, 16, Font.BOLD); Font header = new
 * Font(Font.HELVETICA, 14, Font.BOLD); Font normal = new Font(Font.HELVETICA,
 * 11);
 * 
 * doc.add(new Paragraph(bankName, title)); doc.add(new Paragraph(bankBranch +
 * " | Contact: " + bankContact, normal)); doc.add(Chunk.NEWLINE);
 * 
 * doc.add(new Paragraph("DISBURSEMENT LETTER", header));
 * doc.add(Chunk.NEWLINE);
 * 
 * doc.add(new Paragraph("Date: " + app.getDisbursedDate(), normal));
 * doc.add(new Paragraph("Applicant: " + customer.getName(), normal));
 * doc.add(new Paragraph("Application ID: " + app.getId(), normal));
 * doc.add(Chunk.NEWLINE);
 * 
 * doc.add(new Paragraph("Disbursed Amount: " + app.getDisbursedAmount(),
 * normal)); doc.add(new Paragraph("Interest Rate (Annual): " +
 * app.getAnnualInterestRate() + " %", normal)); doc.add(new
 * Paragraph("Tenure: " + app.getTenureMonths() + " months", normal));
 * doc.add(Chunk.NEWLINE);
 * 
 * doc.add(new Paragraph("Your EMI schedule is available in your portal.",
 * normal)); doc.add(Chunk.NEWLINE);
 * 
 * doc.add(new Paragraph("Authorized Signatory", normal)); doc.add(new
 * Paragraph(bankName, normal));
 * 
 * doc.close(); return baos.toByteArray(); } catch (Exception e) { throw new
 * RuntimeException("Failed to create disbursement PDF: " + e.getMessage(), e);
 * } } }
 */

/*

package com.happytohelp.homeloan.service;

import com.happytohelp.homeloan.model.EmiSchedule;
import com.happytohelp.homeloan.model.LoanApplication;
import com.happytohelp.homeloan.model.User;
import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfWriter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;

@Service
public class PdfLetterService {

    @Value("${app.bank.name}") private String bankName;
    @Value("${app.bank.branch}") private String bankBranch;
    @Value("${app.bank.contact}") private String bankContact;

    public byte[] buildSanctionLetter(LoanApplication app, User customer) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Document doc = new Document(PageSize.A4, 48, 48, 48, 48);

        try {
            PdfWriter.getInstance(doc, baos);
            doc.open();

            Font title = new Font(Font.HELVETICA, 16, Font.BOLD);
            Font header = new Font(Font.HELVETICA, 14, Font.BOLD);
            Font normal = new Font(Font.HELVETICA, 11);

            doc.add(new Paragraph(bankName, title));
            doc.add(new Paragraph(bankBranch + " | Contact: " + bankContact, normal));
            doc.add(Chunk.NEWLINE);

            doc.add(new Paragraph("SANCTION LETTER", header));
            doc.add(Chunk.NEWLINE);

            doc.add(new Paragraph("Date: " + app.getSanctionedDate(), normal));
            doc.add(new Paragraph("Applicant: " + customer.getName(), normal));
            doc.add(new Paragraph("Email: " + customer.getEmail(), normal));
            doc.add(new Paragraph("Application ID: " + app.getId(), normal));
            doc.add(Chunk.NEWLINE);

            doc.add(new Paragraph("Sanctioned Amount: " + app.getSanctionedAmount(), normal));
            doc.add(new Paragraph("Interest Rate (Annual): " + app.getAnnualInterestRate() + " %", normal));
            doc.add(new Paragraph("Tenure: " + app.getTenureMonths() + " months", normal));
            doc.add(Chunk.NEWLINE);

            doc.add(new Paragraph("Property Address: " + app.getPropertyAddress(), normal));
            doc.add(new Paragraph("Remarks/Conditions: " + (app.getAdminRemarks() == null ? "-" : app.getAdminRemarks()), normal));
            doc.add(Chunk.NEWLINE);

            doc.add(new Paragraph("Authorized Signatory", normal));
            doc.add(new Paragraph(bankName, normal));

            doc.close();
            return baos.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException("Failed to create sanction PDF: " + e.getMessage(), e);
        }
    }

    public byte[] buildDisbursementLetter(LoanApplication app, User customer) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Document doc = new Document(PageSize.A4, 48, 48, 48, 48);

        try {
            PdfWriter.getInstance(doc, baos);
            doc.open();

            Font title = new Font(Font.HELVETICA, 16, Font.BOLD);
            Font header = new Font(Font.HELVETICA, 14, Font.BOLD);
            Font normal = new Font(Font.HELVETICA, 11);

            doc.add(new Paragraph(bankName, title));
            doc.add(new Paragraph(bankBranch + " | Contact: " + bankContact, normal));
            doc.add(Chunk.NEWLINE);

            doc.add(new Paragraph("DISBURSEMENT LETTER", header));
            doc.add(Chunk.NEWLINE);

            doc.add(new Paragraph("Date: " + app.getDisbursedDate(), normal));
            doc.add(new Paragraph("Applicant: " + customer.getName(), normal));
            doc.add(new Paragraph("Application ID: " + app.getId(), normal));
            doc.add(Chunk.NEWLINE);

            doc.add(new Paragraph("Disbursed Amount: " + app.getDisbursedAmount(), normal));
            doc.add(new Paragraph("Interest Rate (Annual): " + app.getAnnualInterestRate() + " %", normal));
            doc.add(new Paragraph("Tenure: " + app.getTenureMonths() + " months", normal));
            doc.add(Chunk.NEWLINE);

            doc.add(new Paragraph("Your EMI schedule is available in your portal.", normal));
            doc.add(Chunk.NEWLINE);

            doc.add(new Paragraph("Authorized Signatory", normal));
            doc.add(new Paragraph(bankName, normal));

            doc.close();
            return baos.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException("Failed to create disbursement PDF: " + e.getMessage(), e);
        }
    }

    // NEW: Receipt PDF
    public byte[] buildReceiptPdf(LoanApplication app, User customer, EmiSchedule emi) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Document doc = new Document(PageSize.A4, 48, 48, 48, 48);

        try {
            PdfWriter.getInstance(doc, baos);
            doc.open();

            Font title = new Font(Font.HELVETICA, 16, Font.BOLD);
            Font header = new Font(Font.HELVETICA, 14, Font.BOLD);
            Font normal = new Font(Font.HELVETICA, 11);

            doc.add(new Paragraph(bankName, title));
            doc.add(new Paragraph(bankBranch + " | Contact: " + bankContact, normal));
            doc.add(Chunk.NEWLINE);

            doc.add(new Paragraph("EMI PAYMENT RECEIPT", header));
            doc.add(Chunk.NEWLINE);

            doc.add(new Paragraph("Application ID: " + app.getId(), normal));
            doc.add(new Paragraph("Customer: " + customer.getName() + " (" + customer.getEmail() + ")", normal));
            doc.add(new Paragraph("Installment No: " + emi.getInstallmentNo(), normal));
            doc.add(new Paragraph("Paid Date: " + emi.getPaidDate(), normal));
            doc.add(Chunk.NEWLINE);

            doc.add(new Paragraph("EMI Amount: " + emi.getEmiAmount(), normal));
            doc.add(new Paragraph("Principal Component: " + emi.getPrincipalComponent(), normal));
            doc.add(new Paragraph("Interest Component: " + emi.getInterestComponent(), normal));
            doc.add(Chunk.NEWLINE);

            doc.add(new Paragraph("Payment Method: " + emi.getPayment().getMethod(), normal));
            doc.add(new Paragraph("Txn Ref: " + (emi.getPayment().getTxnRef() == null ? "-" : emi.getPayment().getTxnRef()), normal));

            doc.add(Chunk.NEWLINE);
            doc.add(new Paragraph("Authorized Signatory", normal));
            doc.add(new Paragraph(bankName, normal));

            doc.close();
            return baos.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException("Failed to create receipt PDF: " + e.getMessage(), e);
        }
    }
*/



package com.happytohelp.homeloan.service;

import com.happytohelp.homeloan.model.EmiSchedule;
import com.happytohelp.homeloan.model.LoanApplication;
import com.happytohelp.homeloan.model.User;
import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfWriter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;

@Service
public class PdfLetterService {

    @Value("${app.bank.name}") private String bankName;
    @Value("${app.bank.branch}") private String bankBranch;
    @Value("${app.bank.contact}") private String bankContact;

    public byte[] buildSanctionLetter(LoanApplication app, User customer) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Document doc = new Document(PageSize.A4, 48, 48, 48, 48);

        try {
            PdfWriter.getInstance(doc, baos);
            doc.open();

            Font title = new Font(Font.HELVETICA, 16, Font.BOLD);
            Font header = new Font(Font.HELVETICA, 14, Font.BOLD);
            Font normal = new Font(Font.HELVETICA, 11);

            doc.add(new Paragraph(bankName, title));
            doc.add(new Paragraph(bankBranch + " | Contact: " + bankContact, normal));
            doc.add(Chunk.NEWLINE);

            doc.add(new Paragraph("SANCTION LETTER", header));
            doc.add(Chunk.NEWLINE);

            doc.add(new Paragraph("Date: " + app.getSanctionedDate(), normal));
            doc.add(new Paragraph("Applicant: " + customer.getName(), normal));
            doc.add(new Paragraph("Email: " + customer.getEmail(), normal));
            doc.add(new Paragraph("Application ID: " + app.getId(), normal));
            doc.add(Chunk.NEWLINE);

            doc.add(new Paragraph("Sanctioned Amount: " + app.getSanctionedAmount(), normal));
            doc.add(new Paragraph("Interest Rate (Annual): " + app.getAnnualInterestRate() + " %", normal));
            doc.add(new Paragraph("Tenure: " + app.getTenureMonths() + " months", normal));
            doc.add(Chunk.NEWLINE);

            doc.add(new Paragraph("Property Address: " + app.getPropertyAddress(), normal));
            doc.add(new Paragraph("Remarks/Conditions: " + (app.getAdminRemarks() == null ? "-" : app.getAdminRemarks()), normal));
            doc.add(Chunk.NEWLINE);

            doc.add(new Paragraph("Authorized Signatory", normal));
            doc.add(new Paragraph(bankName, normal));

            doc.close();
            return baos.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException("Failed to create sanction PDF: " + e.getMessage(), e);
        }
    }

    public byte[] buildDisbursementLetter(LoanApplication app, User customer) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Document doc = new Document(PageSize.A4, 48, 48, 48, 48);

        try {
            PdfWriter.getInstance(doc, baos);
            doc.open();

            Font title = new Font(Font.HELVETICA, 16, Font.BOLD);
            Font header = new Font(Font.HELVETICA, 14, Font.BOLD);
            Font normal = new Font(Font.HELVETICA, 11);

            doc.add(new Paragraph(bankName, title));
            doc.add(new Paragraph(bankBranch + " | Contact: " + bankContact, normal));
            doc.add(Chunk.NEWLINE);

            doc.add(new Paragraph("DISBURSEMENT LETTER", header));
            doc.add(Chunk.NEWLINE);

            doc.add(new Paragraph("Date: " + app.getDisbursedDate(), normal));
            doc.add(new Paragraph("Applicant: " + customer.getName(), normal));
            doc.add(new Paragraph("Application ID: " + app.getId(), normal));
            doc.add(Chunk.NEWLINE);

            doc.add(new Paragraph("Disbursed Amount: " + app.getDisbursedAmount(), normal));
            doc.add(new Paragraph("Interest Rate (Annual): " + app.getAnnualInterestRate() + " %", normal));
            doc.add(new Paragraph("Tenure: " + app.getTenureMonths() + " months", normal));
            doc.add(Chunk.NEWLINE);

            doc.add(new Paragraph("Your EMI schedule is available in your portal.", normal));
            doc.add(Chunk.NEWLINE);

            doc.add(new Paragraph("Authorized Signatory", normal));
            doc.add(new Paragraph(bankName, normal));

            doc.close();
            return baos.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException("Failed to create disbursement PDF: " + e.getMessage(), e);
        }
    }

    // NEW: Receipt PDF
    public byte[] buildReceiptPdf(LoanApplication app, User customer, EmiSchedule emi) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Document doc = new Document(PageSize.A4, 48, 48, 48, 48);

        try {
            PdfWriter.getInstance(doc, baos);
            doc.open();

            Font title = new Font(Font.HELVETICA, 16, Font.BOLD);
            Font header = new Font(Font.HELVETICA, 14, Font.BOLD);
            Font normal = new Font(Font.HELVETICA, 11);

            doc.add(new Paragraph(bankName, title));
            doc.add(new Paragraph(bankBranch + " | Contact: " + bankContact, normal));
            doc.add(Chunk.NEWLINE);

            doc.add(new Paragraph("EMI PAYMENT RECEIPT", header));
            doc.add(Chunk.NEWLINE);

            doc.add(new Paragraph("Application ID: " + app.getId(), normal));
            doc.add(new Paragraph("Customer: " + customer.getName() + " (" + customer.getEmail() + ")", normal));
            doc.add(new Paragraph("Installment No: " + emi.getInstallmentNo(), normal));
            doc.add(new Paragraph("Paid Date: " + emi.getPaidDate(), normal));
            doc.add(Chunk.NEWLINE);

            doc.add(new Paragraph("EMI Amount: " + emi.getEmiAmount(), normal));
            doc.add(new Paragraph("Principal Component: " + emi.getPrincipalComponent(), normal));
            doc.add(new Paragraph("Interest Component: " + emi.getInterestComponent(), normal));
            doc.add(Chunk.NEWLINE);

            doc.add(new Paragraph("Payment Method: " + emi.getPayment().getMethod(), normal));
            doc.add(new Paragraph("Txn Ref: " + (emi.getPayment().getTxnRef() == null ? "-" : emi.getPayment().getTxnRef()), normal));

            doc.add(Chunk.NEWLINE);
            doc.add(new Paragraph("Authorized Signatory", normal));
            doc.add(new Paragraph(bankName, normal));

            doc.close();
            return baos.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException("Failed to create receipt PDF: " + e.getMessage(), e);
        }
    }
}