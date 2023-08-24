package com.group3.twat.controller;

import com.group3.twat.model.post.service.TwattService;
import com.group3.twat.model.post.Twatt;
import org.apache.coyote.Request;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;


import java.time.LocalDate;
import java.util.List;
@RestController
@RequestMapping("/twatts")
public class TwattController {
        private final TwattService twattService;

        @Autowired
        public TwattController(TwattService twattService) {
            this.twattService = twattService;
        }

    @GetMapping()
    public List<Twatt> getTwatts() {
        return twattService.getAllTwats(true);
    }

    @GetMapping("/{twattId}")
    public Twatt getTwattById(@PathVariable Long twattId){ return twattService.getTwattById(twattId); }

    @PostMapping()
    public ResponseEntity<?> addTwatt(@RequestBody Twatt newTwatt) {
        twattService.addTwatt(newTwatt);
        System.out.println(newTwatt.getText());
        return ResponseEntity.ok().build();
    }


    @DeleteMapping("/{twatId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<String> deleteTwattById(@PathVariable Long twatId) {
        boolean deleted = twattService.deleteTwattById(twatId);
        if (deleted) {
            return ResponseEntity.ok("Twatt deleted successfully");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/replies/{parentId}")
    public List<Twatt> getReplies(@PathVariable Long parentId){
            return twattService.getReplies(parentId);
    }

}
