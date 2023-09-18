package com.group3.twat.twatt;

import com.group3.twat.requests.NewTwattRequest;
import com.group3.twat.twatt.service.TwattService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.access.prepost.PreAuthorize;
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
    public ResponseEntity<List<Twatt>> getTwatts() {
        System.out.println("getTwatts");
        return ResponseEntity.ok(twattService.getAllTwats(true));
    }

    @GetMapping("/{twattId}")
    public Twatt getTwattById(@PathVariable Long twattId){ return twattService.getTwattById(twattId); }

    @PostMapping()
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> addTwatt(@RequestBody NewTwattRequest newTwattRequest) {
            Twatt twatt = new Twatt(null,
                    null,
                    newTwattRequest.body(),
                    LocalDate.now(),
                    newTwattRequest.parentId()
            );
            twattService.addTwatt(twatt);
        return ResponseEntity.ok().build();
    }


    @DeleteMapping("/{twatId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<String> deleteTwattById(@PathVariable Long twatId) {
        boolean deleted = twattService.deleteTwattById(twatId);
        if (deleted) {
            return ResponseEntity.ok("Twatt deleted successfully");
        }
        return ResponseEntity.notFound().build();

    }

    @GetMapping("/replies/{parentId}")
    public List<Twatt> getReplies(@PathVariable Long parentId){
            return twattService.getReplies(parentId);
    }

}
