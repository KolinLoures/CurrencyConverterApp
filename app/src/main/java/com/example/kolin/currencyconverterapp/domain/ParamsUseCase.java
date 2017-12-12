package com.example.kolin.currencyconverterapp.domain;

/**
 * Interface indecates that class work with params
 */

public interface ParamsUseCase<P extends Params> {

    void setParams(P params);

    P getParams();

}
