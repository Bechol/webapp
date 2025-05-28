package ru.testtask.webapp.config;

import org.hibernate.boot.model.naming.CamelCaseToUnderscoresNamingStrategy;
import org.hibernate.boot.model.naming.Identifier;
import org.hibernate.engine.jdbc.env.spi.JdbcEnvironment;

public class JpaNamingStrategyConfig extends CamelCaseToUnderscoresNamingStrategy {

    private static final String PREFIX = "tst_";

    @Override
    public Identifier toPhysicalTableName(Identifier name, JdbcEnvironment jdbcEnvironment) {
        String nameText = name.getText();
        String tableName = nameText.startsWith(PREFIX) ? nameText : PREFIX + nameText;
        return super.toPhysicalTableName(Identifier.toIdentifier(tableName), jdbcEnvironment);
    }

    @Override
    public Identifier toPhysicalSequenceName(Identifier name, JdbcEnvironment jdbcEnvironment) {
        String nameText = name.getText();
        String sequenceName = nameText.startsWith(PREFIX) ? nameText : PREFIX + nameText;
        return super.toPhysicalSequenceName(Identifier.toIdentifier(sequenceName), jdbcEnvironment);
    }
}
