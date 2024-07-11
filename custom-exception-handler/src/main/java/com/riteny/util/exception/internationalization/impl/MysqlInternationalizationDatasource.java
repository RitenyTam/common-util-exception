package com.riteny.util.exception.internationalization.impl;

import com.riteny.util.exception.internationalization.InternationalizationDatasource;

import javax.sql.DataSource;

public class MysqlInternationalizationDatasource implements InternationalizationDatasource {

    private DataSource dataSource;

    public MysqlInternationalizationDatasource(DataSource dataSource) {

    }

    @Override
    public String getValue(String index, String lang) {
        return "";
    }
}
