package com.idealista.scorechallenge.infrastructure.inmemory.database;

import com.idealista.scorechallenge.domain.model.Advertisement;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class AdvertisementDatabase extends BaseDatabase<Advertisement, UUID> {
}
