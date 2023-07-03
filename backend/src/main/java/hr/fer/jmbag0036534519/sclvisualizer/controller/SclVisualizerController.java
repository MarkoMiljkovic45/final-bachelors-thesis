package hr.fer.jmbag0036534519.sclvisualizer.controller;

import hr.fer.jmbag0036534519.sclvisualizer.parser.ParserResponse;
import hr.fer.jmbag0036534519.sclvisualizer.service.SclParserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/scl/")
@CrossOrigin
public class SclVisualizerController {

    private final SclParserService sclParserService;

    @Autowired
    public SclVisualizerController(SclParserService sclParserService) {
        this.sclParserService = sclParserService;
    }

    @PostMapping("/parser")
    public ResponseEntity<String> parseFile(@RequestBody String sclText) {
        ParserResponse parserResponse = sclParserService.parseScl(sclText);

        String response = String.format(
                """
                {
                   "error": "%s",
                   "scl": %s
                }
                """
        ,parserResponse.error(), parserResponse.sclJson());

        return ResponseEntity.ok(response);
    }
}
