package com.example.kolin.currencyconverterapp.domain;

/**
 * Created by kolin on 09.12.2017.
 */

public interface ParamsUseCase<P extends Params> {

    void setParams(P params);

    P getParams();

}
