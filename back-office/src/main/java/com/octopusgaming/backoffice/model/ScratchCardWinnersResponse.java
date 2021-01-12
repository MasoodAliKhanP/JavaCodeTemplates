package com.octopusgaming.backoffice.model;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ScratchCardWinnersResponse {
	private List<ScratchCardWinner> winners;
}
