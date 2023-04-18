/**
 * Copyright (c) 2010-2023 Contributors to the openHAB project
 *
 * See the NOTICE file(s) distributed with this work for additional
 * information.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0
 *
 * SPDX-License-Identifier: EPL-2.0
 */
package org.openhab.automation.jrule.internal.module;

import org.openhab.core.automation.ModuleHandlerCallback;
import org.openhab.core.automation.Trigger;
import org.openhab.core.automation.handler.BaseTriggerModuleHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Arne Seime - Initial Contribution
 */
public class JRuleTriggerHandler extends BaseTriggerModuleHandler {

    public static final String TRIGGER_PREFIX = JRuleRuleProvider.MODULE_PREFIX + "trigger.";
    private JRuleRuleProvider jRuleRuleProvider;
    private String ruleUID;

    private final Logger logger = LoggerFactory.getLogger(JRuleTriggerHandler.class);

    public JRuleTriggerHandler(Trigger module, JRuleRuleProvider jRuleRuleProvider, String ruleUID) {
        super(module);
        this.jRuleRuleProvider = jRuleRuleProvider;
        this.ruleUID = ruleUID;
    }

    @Override
    public void setCallback(ModuleHandlerCallback callback) {
        JRuleModuleEntry rule = jRuleRuleProvider.getRule(ruleUID);
        if (rule != null) {
            rule.ruleEnabled();
        } else {
            logger.error("Did not find rule {}, unable to enable", ruleUID);
        }
    }

    @Override
    public void dispose() {
        super.dispose();
        JRuleModuleEntry rule = jRuleRuleProvider.getRule(ruleUID);
        if (rule != null) {
            rule.ruleDisabled();
        } else {
            logger.error("Did not find rule {}, unable to disable", ruleUID);
        }
    }
}
