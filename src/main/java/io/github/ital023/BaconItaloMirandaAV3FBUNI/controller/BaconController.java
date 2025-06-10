package io.github.ital023.BaconItaloMirandaAV3FBUNI.controller;

import io.github.ital023.BaconItaloMirandaAV3FBUNI.Service.BaconService;
import io.github.ital023.BaconItaloMirandaAV3FBUNI.controller.dto.ConnectionRequestDto;
import io.github.ital023.BaconItaloMirandaAV3FBUNI.controller.dto.ConnectionResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
public class BaconController {

    private final BaconService baconService;

    public BaconController(BaconService baconService) {
        this.baconService = baconService;
    }


    @PostMapping("/connect")
    public ResponseEntity<ConnectionResponse> getConnection(@RequestBody ConnectionRequestDto dto) {

        ConnectionResponse response = baconService.findConnection(dto.startActor(), dto.endActor());
        return ResponseEntity.ok(response);
    }
}
