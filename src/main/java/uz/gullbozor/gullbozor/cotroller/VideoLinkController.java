package uz.gullbozor.gullbozor.cotroller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.gullbozor.gullbozor.apiResponse.ApiResponse;
import uz.gullbozor.gullbozor.entity.VideoLink;
import uz.gullbozor.gullbozor.service.VideoLinkService;

import java.util.List;

@RestController
@RequestMapping("/videoLink")
public class VideoLinkController {

    @Autowired
    private VideoLinkService videoLinkService;

//    @PreAuthorize(value = "hasAnyRole('ROLE_ADMIN','ROLE_USER')")
    @PostMapping
    public ResponseEntity<ApiResponse> addVideoLink(@RequestBody VideoLink videoLink ) {
        ApiResponse apiResponse = videoLinkService.addVideoLink(videoLink);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 400).body(apiResponse);
    }

//    @PreAuthorize(value = "hasAnyRole('ROLE_ADMIN','ROLE_USER')")
    @PutMapping
    public ResponseEntity<ApiResponse> editVideoLink(@RequestBody VideoLink videoLink, @PathVariable Long id) {
        ApiResponse apiResponse = videoLinkService.editVideoLink(videoLink, id);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 400).body(apiResponse);
    }

//    @PreAuthorize(value = "hasAnyRole('ROLE_ADMIN','ROLE_USER')")
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getVideoLinkById(@PathVariable Long id) {
        ApiResponse apiResponse = videoLinkService.getVideoLinkById(id);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 400).body(apiResponse);
    }

//    @PreAuthorize(value = "hasAnyRole('ROLE_ADMIN','ROLE_USER')")
    @GetMapping
    public ResponseEntity<?> getVideoLinkPage(@RequestParam int page) {
        Page<VideoLink> videoLinkPage = videoLinkService.getVideoLinkPage(page);
        return ResponseEntity.ok(videoLinkPage);
    }

//    @PreAuthorize(value = "hasAnyRole('ROLE_ADMIN','ROLE_USER')")
    @GetMapping("/byCategoryId/{categoryId}")
    public ResponseEntity<?> getVideoByCategoryId(@PathVariable Long categoryId, @RequestParam int page) {
        Page<VideoLink> videoPageByCategoryId = videoLinkService.getVideoPageByCategoryId(page, categoryId);
        return ResponseEntity.ok(videoPageByCategoryId);
    }

//    @PreAuthorize(value = "hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteById(@PathVariable Long id) {
        ApiResponse apiResponse = videoLinkService.deleteById(id);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 400).body(apiResponse);
    }

//    @PreAuthorize(value = "hasRole('ROLE_ADMIN')")
    @DeleteMapping("deleteAll/{pin}")
    public ResponseEntity<ApiResponse> deleteAllByPin(@PathVariable Integer pin) {
        ApiResponse apiResponse = videoLinkService.deleteAllByPin(pin);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 400).body(apiResponse);
    }

}
