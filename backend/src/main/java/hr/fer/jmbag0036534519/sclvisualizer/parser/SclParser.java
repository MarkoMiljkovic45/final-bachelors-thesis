package hr.fer.jmbag0036534519.sclvisualizer.parser;

import org.w3c.dom.*;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.*;
import java.io.IOException;
import java.util.Stack;

public class SclParser {
    private static final int TAB_SIZE = 3;

    public static String parse(InputSource sclInputSource) throws IOException, SAXException, ParserConfigurationException {
        final Document xmlDocument;

        SAXParser saxParser;

        SAXParserFactory factory = SAXParserFactory.newInstance();
        saxParser = factory.newSAXParser();
        DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = docBuilderFactory.newDocumentBuilder();
        xmlDocument = documentBuilder.newDocument();

        final Stack<Element> elementStack = new Stack<>();
        DefaultHandler handler = new DefaultHandler() {

            private Locator documentLocator;

            private final StringBuilder textNodeContent = new StringBuilder();

            @Override
            public void setDocumentLocator(Locator locator) {
                documentLocator = locator;
            }

            @Override
            public void startElement(String uri, String localName, String qName, Attributes attributes) {

                if (textNodeContent.length() > 0)
                    createTextNode();

                Element element = xmlDocument.createElement(qName);

                element.setUserData("START_LINE_NUMBER_ATTR", documentLocator.getLineNumber(), null);
                element.setUserData("START_COLUMN_NUMBER_ATTR", documentLocator.getColumnNumber(), null);

                for (int i = 0; i < attributes.getLength(); i++) {
                    element.setAttribute(attributes.getQName(i), attributes.getValue(i));
                }

                elementStack.push(element);
            }

            @Override
            public void endElement(String uri, String localName, String qName) {

                if (textNodeContent.length() > 0)
                    createTextNode();

                Element element = elementStack.pop();

                element.setUserData("END_LINE_NUMBER_ATTR", documentLocator.getLineNumber(), null);
                element.setUserData("END_COLUMN_NUMBER_ATTR", documentLocator.getColumnNumber(), null);

                if (elementStack.isEmpty())
                    xmlDocument.appendChild(element);
                else {
                    Element parentElement = elementStack.peek();
                    parentElement.appendChild(element);
                }
            }

            @Override
            public void characters(char[] ch, int start, int length) {
                textNodeContent.append(ch, start, length);
            }

            private void createTextNode() {
                Element element = elementStack.peek();
                Node textNode = xmlDocument.createTextNode(textNodeContent.toString());
                element.appendChild(textNode);
                textNodeContent.delete(0, textNodeContent.length());
            }
        };

        saxParser.parse(sclInputSource, handler);

        Node root = xmlDocument.getFirstChild();
        return xmlObjectModelToJson(root);
    }

    private static String xmlObjectModelToJson(Node root) {
        StringBuilder sb = new StringBuilder();
        printNode(root, sb, 0, true);
        return sb.toString();
    }

    private static void printNode(Node node, StringBuilder sb, int depth, boolean last) {
        if (node.getNodeType() != Node.ELEMENT_NODE) {
            return;
        }

        String tab = " ".repeat(TAB_SIZE);
        String initialIndent = tab.repeat(depth);
        String indent = tab.repeat(depth + 1);

        sb.append(initialIndent).append("{\n");
        sb.append(indent).append("\"name\": \"").append(node.getNodeName()).append("\",\n");

        NamedNodeMap map = node.getAttributes();
        int mapLen = map.getLength();

        if (mapLen == 0) {
            sb.append(indent).append("\"args\": {},\n");
        } else {
            sb.append(indent).append("\"args\": {\n");

            for (int i = 0; i < mapLen; i++) {
                Node attr = map.item(i);
                sb.append(indent).append(tab)
                        .append("\"").append(attr.getNodeName()).append("\": \"")
                        .append(attr.getNodeValue()).append("\"");

                if (i + 1 == mapLen) {
                    sb.append("\n");
                } else {
                    sb.append(",\n");
                }
            }

            sb.append(indent).append("},\n");
        }

        Node textNode = node.getFirstChild();
        if (textNode != null && textNode.getNodeType() == Node.TEXT_NODE) {
            sb.append(indent).append("\"value\": \"").append(textNode.getNodeValue().strip()).append("\",\n");
        } else {
            sb.append(indent).append("\"value\": \"\",\n");
        }

        NodeList children = node.getChildNodes();
        int childrenLen = children.getLength() - 1;

        if (childrenLen == 0) {
            sb.append(indent).append("\"children\": []\n");
        } else {
            sb.append(indent).append("\"children\": [\n");

            for (int i = 0; i < childrenLen; i++) {
                Node child = children.item(i);
                printNode(child, sb,depth + 2, i + 1 == childrenLen);
            }

            sb.append(indent).append("]\n");
        }

        if (last) {
            sb.append(initialIndent).append("}\n");
        } else {
            sb.append(initialIndent).append("},\n");
        }
    }
}
