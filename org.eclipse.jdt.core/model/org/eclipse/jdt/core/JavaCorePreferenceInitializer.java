/*******************************************************************************
 * Copyright (c) 2000, 2004 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials 
 * are made available under the terms of the Common Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/cpl-v10.html
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.jdt.core;

import java.util.*;

import org.eclipse.core.runtime.preferences.*;
import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.jdt.core.formatter.DefaultCodeFormatterConstants;
import org.eclipse.jdt.internal.compiler.impl.CompilerOptions;
import org.eclipse.jdt.internal.core.JavaModelManager;

/**
 * JavaCore eclipse preferences initializer.
 * Initially done in JavaCore.initializeDefaultPreferences which was deprecated
 * with new eclipse preferences mechanism.
 */
public class JavaCorePreferenceInitializer extends AbstractPreferenceInitializer {

	/* (non-Javadoc)
	 * @see org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer#initializeDefaultPreferences()
	 */
	public void initializeDefaultPreferences() {

		// Get options names set
		HashSet optionNames = JavaModelManager.getJavaModelManager().optionNames;
		
		// Compiler settings
		Map defaultOptionsMap = new CompilerOptions().getMap(); // compiler defaults
		
		// Override some compiler defaults
		defaultOptionsMap.put(JavaCore.COMPILER_LOCAL_VARIABLE_ATTR, JavaCore.GENERATE);
		defaultOptionsMap.put(JavaCore.COMPILER_CODEGEN_UNUSED_LOCAL, JavaCore.PRESERVE);
		defaultOptionsMap.put(JavaCore.COMPILER_TASK_TAGS, JavaCore.DEFAULT_TASK_TAGS);
		defaultOptionsMap.put(JavaCore.COMPILER_TASK_PRIORITIES, JavaCore.DEFAULT_TASK_PRIORITIES);
		defaultOptionsMap.put(JavaCore.COMPILER_TASK_CASE_SENSITIVE, JavaCore.ENABLED);
		defaultOptionsMap.put(JavaCore.COMPILER_DOC_COMMENT_SUPPORT, JavaCore.ENABLED);
	
		// Builder settings
		defaultOptionsMap.put(JavaCore.CORE_JAVA_BUILD_RESOURCE_COPY_FILTER, ""); //$NON-NLS-1$
		defaultOptionsMap.put(JavaCore.CORE_JAVA_BUILD_INVALID_CLASSPATH, JavaCore.ABORT); 
		defaultOptionsMap.put(JavaCore.CORE_JAVA_BUILD_DUPLICATE_RESOURCE, JavaCore.WARNING); 
		defaultOptionsMap.put(JavaCore.CORE_JAVA_BUILD_CLEAN_OUTPUT_FOLDER, JavaCore.CLEAN); 

		// JavaCore settings
		defaultOptionsMap.put(JavaCore.CORE_JAVA_BUILD_ORDER, JavaCore.IGNORE); 
		defaultOptionsMap.put(JavaCore.CORE_INCOMPLETE_CLASSPATH, JavaCore.ERROR); 
		defaultOptionsMap.put(JavaCore.CORE_CIRCULAR_CLASSPATH, JavaCore.ERROR); 
		defaultOptionsMap.put(JavaCore.CORE_INCOMPATIBLE_JDK_LEVEL, JavaCore.IGNORE); 
		defaultOptionsMap.put(JavaCore.CORE_ENABLE_CLASSPATH_EXCLUSION_PATTERNS, JavaCore.ENABLED); 
		defaultOptionsMap.put(JavaCore.CORE_ENABLE_CLASSPATH_MULTIPLE_OUTPUT_LOCATIONS, JavaCore.ENABLED); 

		// encoding setting comes from resource plug-in
		optionNames.add(JavaCore.CORE_ENCODING);

		// Formatter settings
		formatterSettings(defaultOptionsMap, optionNames);

		// CodeAssist settings
		defaultOptionsMap.put(JavaCore.CODEASSIST_VISIBILITY_CHECK, JavaCore.DISABLED); //$NON-NLS-1$
		defaultOptionsMap.put(JavaCore.CODEASSIST_IMPLICIT_QUALIFICATION, JavaCore.DISABLED); //$NON-NLS-1$
		defaultOptionsMap.put(JavaCore.CODEASSIST_FIELD_PREFIXES, ""); //$NON-NLS-1$
		defaultOptionsMap.put(JavaCore.CODEASSIST_STATIC_FIELD_PREFIXES, ""); //$NON-NLS-1$
		defaultOptionsMap.put(JavaCore.CODEASSIST_LOCAL_PREFIXES, ""); //$NON-NLS-1$
		defaultOptionsMap.put(JavaCore.CODEASSIST_ARGUMENT_PREFIXES, ""); //$NON-NLS-1$
		defaultOptionsMap.put(JavaCore.CODEASSIST_FIELD_SUFFIXES, ""); //$NON-NLS-1$
		defaultOptionsMap.put(JavaCore.CODEASSIST_STATIC_FIELD_SUFFIXES, ""); //$NON-NLS-1$
		defaultOptionsMap.put(JavaCore.CODEASSIST_LOCAL_SUFFIXES, ""); //$NON-NLS-1$
		defaultOptionsMap.put(JavaCore.CODEASSIST_ARGUMENT_SUFFIXES, ""); //$NON-NLS-1$
		defaultOptionsMap.put(JavaCore.CODEASSIST_RESTRICTIONS_CHECK, JavaCore.DISABLED); //$NON-NLS-1$
		
		// Store default values to default preferences
	 	IEclipsePreferences defaultPreferences = new DefaultScope().getNode(JavaCore.PLUGIN_ID);
		for (Iterator iter = defaultOptionsMap.entrySet().iterator(); iter.hasNext();) {
			Map.Entry entry = (Map.Entry) iter.next();
			String optionName = (String) entry.getKey();
			defaultPreferences.put(optionName, (String)entry.getValue());
			optionNames.add(optionName);
		}
	}
	
	/**
	 * Avoid depraction warnings on formatter settings
	 * @deprecated
	 */
	private void formatterSettings(Map defaultOptionsMap, HashSet optionNames) {
		Map codeFormatterOptionsMap = DefaultCodeFormatterConstants.getJavaConventionsSettings(); // code formatter defaults
		for (Iterator iter = codeFormatterOptionsMap.entrySet().iterator(); iter.hasNext();) {
			Map.Entry entry = (Map.Entry) iter.next();
			String optionName = (String) entry.getKey();
			defaultOptionsMap.put(optionName, entry.getValue());
			optionNames.add(optionName);
		}		
		defaultOptionsMap.put(JavaCore.FORMATTER_NEWLINE_OPENING_BRACE, JavaCore.DO_NOT_INSERT); 
		defaultOptionsMap.put(JavaCore.FORMATTER_NEWLINE_CONTROL, JavaCore.DO_NOT_INSERT);
		defaultOptionsMap.put(JavaCore.FORMATTER_CLEAR_BLANK_LINES, JavaCore.PRESERVE_ONE); 
		defaultOptionsMap.put(JavaCore.FORMATTER_NEWLINE_ELSE_IF, JavaCore.DO_NOT_INSERT);
		defaultOptionsMap.put(JavaCore.FORMATTER_NEWLINE_EMPTY_BLOCK, JavaCore.INSERT); 
		defaultOptionsMap.put(JavaCore.FORMATTER_LINE_SPLIT, "80"); //$NON-NLS-1$
		defaultOptionsMap.put(JavaCore.FORMATTER_COMPACT_ASSIGNMENT, JavaCore.NORMAL); 
		defaultOptionsMap.put(JavaCore.FORMATTER_TAB_CHAR, JavaCore.TAB); 
		defaultOptionsMap.put(JavaCore.FORMATTER_TAB_SIZE, "4"); //$NON-NLS-1$ 
		defaultOptionsMap.put(JavaCore.FORMATTER_SPACE_CASTEXPRESSION, JavaCore.INSERT); //$NON-NLS-1$ 
	}
}
