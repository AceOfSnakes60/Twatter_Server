package com.group3.twat.twatt;

import com.group3.twat.group.service.GroupService;
import com.group3.twat.requests.NewTwattRequest;
import com.group3.twat.twatt.model.Twatt;
import com.group3.twat.twatt.model.TwattPublicDTO;
import com.group3.twat.twatt.service.TwattService;
import com.group3.twat.user.model.User;
import com.group3.twat.user.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;


import java.time.LocalDate;
import java.util.List;


@RestController
@RequestMapping("/twatts")
public class TwattController {
        private final TwattService twattService;
        @Autowired
        private UserService userService;
        @Autowired
        private GroupService groupService;

        @Autowired
        public TwattController(TwattService twattService) {
            this.twattService = twattService;
        }


    @GetMapping()
    public ResponseEntity<Page<Twatt>> getTwatts(@RequestParam int page) {
        Pageable pageable = PageRequest.of(page, 10, Sort.by(Sort.Order.desc("date")));
        Page<Twatt> twatts = twattService.getTwattByPage(pageable);
        return ResponseEntity.ok(twatts);
    }

    @GetMapping("/{twattId}")
    public Twatt getTwattById(@PathVariable Long twattId){ return twattService.getTwattById(twattId); }


    @GetMapping("/groups/{id}")
    public List<Twatt> getGroupTwatts(@PathVariable Long groupId, @RequestParam int page){
            return groupService.getTwatsFromGroup(groupId, page);
    }

    //TODO Add Pagination
    @GetMapping("/user/{username}")
    public List<TwattPublicDTO> getUserTwatts(@PathVariable String username){
        System.out.println("Get by username: " + username);
            List<TwattPublicDTO> twattList = twattService.getByUsername(username, 0);
        System.out.println(twattList.get(0));
        return twattList;
    }

    @PostMapping()
    public ResponseEntity<?> addTwatt(@RequestBody NewTwattRequest newTwattRequest) {
        System.out.println("Post twatt");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userService.getUserByName(username);
        System.out.println(user);
            Twatt twatt = new Twatt(null,
                    user,
                    newTwattRequest.body(),
                    LocalDate.now(),
                    newTwattRequest.parentId()
            );
        System.out.println(twatt);
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
    public List<TwattPublicDTO> getReplies(@PathVariable Long parentId){
        System.out.println("GetReplies");
            return twattService.getReplies(parentId);
    }

    @PostMapping("/like")
    public ResponseEntity<?> likeTwatt(@RequestParam Long twattId){
            //TODO
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userService.getUserByName(username);

        twattService.addLike(twattId);

        return ResponseEntity.ok().build();
    }

}
