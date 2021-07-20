package com.truelayer.translation.services;

public interface IMapper<From,To> {
    To map(From from);
}
