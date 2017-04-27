package sample;

import java.io.File;
import java.util.List;
import java.util.Iterator;
import java.io.FileOutputStream;
import java.io.IOException;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.*;
import org.dom4j.io.*;


/**
 * Created by incloud on 2017/4/4.
 */
public class XMLHandler {

    public Document readFile (String fileStr) {
        try {
            File inputFile = new File(fileStr);
            SAXReader reader = new SAXReader();
            Document document = reader.read(inputFile);
            return document;
        }  catch (DocumentException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Node> getNode(Document document, String nodeMsg) {
        List<Node> nodes = document.selectNodes(nodeMsg);
        return nodes;
    }

    public xmlMsg addNode(Document document, String name, String phone, String gender, String age) {
        Element studentMsgElement = document.getRootElement();
        Element newStu = studentMsgElement.addElement("person");
        Element na = newStu.addElement("name");
        na.setText(name);
        Element ag = newStu.addElement("gender");
        ag.setText(gender);
        Element em = newStu.addElement("age");
        em.setText(age);
        Element gr = newStu.addElement("phone");
        gr.setText(phone);

        xmlMsg node = new xmlMsg(na.getText(), ag.getText(), em.getText(), gr.getText());
        return node;
    }

    public Element searchNodeMsg (Document document, String searchStr) {
        Element rootElement = document.getRootElement();    // 拿到文件树的根节点
        List persons = rootElement.elements();
        // 遍历整个链表
        for (int i = 0; i < persons.size(); i++) {
            Element person = (Element) persons.get(i);
            for (Iterator it = person.elementIterator(); it.hasNext();) {
                Element node = (Element) it.next();
                if (node.getName().equals("name")) {
                    if (node.getText().equals(searchStr)) {
                        return person;
                    }
                }
            }
        }
        return null;
    }

    public String outputMethon(Document document){
        try {
            //实例化输出格式对象
            OutputFormat format = OutputFormat.createPrettyPrint();
            //设置输出编码
            format.setEncoding("UTF-8");
            //创建需要写入的File对象
            File file = new File("/Users/incloud/Desktop/" + File.separator + "final.xml");
            //生成XMLWriter对象，构造函数中的参数为需要输出的文件流和格式
            XMLWriter writer = new XMLWriter(new FileOutputStream(file), format);
            //开始写入，write方法中包含上面创建的Document对象
            writer.write(document);
            return file.getPath();
        }
        catch(IOException e){
            e.printStackTrace();
            return null;
        }
    }

}
