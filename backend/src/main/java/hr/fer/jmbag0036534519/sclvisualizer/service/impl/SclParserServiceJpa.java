package hr.fer.jmbag0036534519.sclvisualizer.service.impl;

import hr.fer.jmbag0036534519.sclvisualizer.parser.ParserResponse;
import hr.fer.jmbag0036534519.sclvisualizer.parser.SclParser;
import hr.fer.jmbag0036534519.sclvisualizer.service.SclParserService;
import org.springframework.stereotype.Service;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.StringReader;

@Service
public class SclParserServiceJpa implements SclParserService {
    @Override
    public ParserResponse parseScl(String sclText) {
        try {
            String sclJson = SclParser.parse(new InputSource(new StringReader(sclText)));
            return new ParserResponse(sclJson, "");
        }
        catch (SAXException sax) {
            return new ParserResponse(null, "Invalid SCL file");
        }
        catch (Exception e) {
            return new ParserResponse(null, "Internal server error");
        }
    }
}
