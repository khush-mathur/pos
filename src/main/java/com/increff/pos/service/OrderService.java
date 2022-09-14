package com.increff.pos.service;

import com.increff.pos.dao.OrderDao;
import com.increff.pos.exception.ApiException;
import com.increff.pos.model.data.OrderItemData;
import com.increff.pos.pojo.OrderPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.transform.*;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.stream.StreamSource;
import java.io.*;
import java.nio.file.Files;
import java.time.ZonedDateTime;
import java.util.List;

@Service
public class OrderService {
    @Autowired
    OrderDao orderDao;

    @Autowired
    OrderItemService orderItemService;
    public OrderPojo getById(Integer id) throws ApiException{
        OrderPojo pojo = orderDao.getByID(id);
        if(pojo==null)
            new ApiException("No order exist by Id : " + id);
        return orderDao.getByID(id);
    }

    public List<OrderPojo> getAllOrders(){
        return orderDao.selectAll();
    }

    @Transactional
    public void add(OrderPojo order) throws ApiException {
            orderDao.add(order);
    }

    @Transactional(rollbackOn = ApiException.class)
    public OrderPojo addOrder() {
        OrderPojo pojo = new OrderPojo();
        pojo.setDateTime(ZonedDateTime.now());
        return orderDao.add(pojo);
    }

    @Transactional
    public void getOrderInvoice(Integer orderId,List<OrderItemData> orderItemDataList) throws ApiException {
        ZonedDateTime time = getById(orderId).getDateTime();
        double total = 0.;
        for (OrderItemData itemData : orderItemDataList) {
            total += itemData.getQuantity() * itemData.getSellingPrice();
        }
        InvoiceData invoiceData = new InvoiceData(orderItemDataList, time, total, orderId);
        String invoice = "main/resources/Invoice/invoice" + orderId + ".pdf";
        String xml = jaxbObjectToXML(invoiceData);
        File xsltFile = new File("src", "main/resources/com/increff/pos/invoice.xml");
        File pdfFile = new File("src", invoice);
        try {
            convertToPDF(xsltFile, pdfFile, xml);
        } catch (IOException e) {
            throw new ApiException(e.getMessage());
        }
    }
    private static String jaxbObjectToXML(InvoiceData invoiceData) {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(InvoiceData.class);
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            StringWriter stringWriter = new StringWriter();
            jaxbMarshaller.marshal(invoiceData, stringWriter);
            return stringWriter.toString();
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return "";
    }

    private void convertToPDF(File xslt, File pdf, String xml) throws ApiException, IOException {
        OutputStream out = null;
        try {
            FOUserAgent foUserAgent = fopFactory.newFOUserAgent();
            out = Files.newOutputStream(pdf.toPath());
            out = new java.io.BufferedOutputStream(out);
            Fop fop = fopFactory.newFop(MimeConstants.MIME_PDF, foUserAgent, out);
            TransformerFactory factory = TransformerFactory.newInstance();
            Transformer transformer = factory.newTransformer(new StreamSource(xslt));
            Source src = new StreamSource(new StringReader(xml));
            Result res = new SAXResult(fop.getDefaultHandler());
            transformer.transform(src, res);
        } catch (FOPException e) {
            System.out.println("FOP Exception");
            throw new ApiException(e.getMessage());
        } catch (TransformerException e) {
            System.out.println("Transformer Exception");
            throw new ApiException(e.getMessage());
        } catch (IOException e) {
            throw new ApiException(e.getMessage());
        } finally {
            out.close();
        }
    }
}
