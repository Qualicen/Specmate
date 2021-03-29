package com.specmate.emfrest.internal;

import org.glassfish.jersey.server.ResourceConfig;

import com.specmate.emfrest.internal.auth.AuthenticationFilter;
import com.specmate.emfrest.internal.metrics.MetricsDynamicFeature;
import com.specmate.emfrest.internal.rest.JsonEObjectWriter;
import com.specmate.emfrest.internal.rest.JsonListWriter;
import com.specmate.emfrest.internal.rest.JsonMapWriter;
import com.specmate.emfrest.internal.rest.JsonReader;
import com.specmate.emfrest.internal.rest.RootResource;

class EmfRestJerseyApplication extends ResourceConfig {

	public EmfRestJerseyApplication() {
		registerClasses(RootResource.class, JsonEObjectWriter.class, JsonListWriter.class, JsonMapWriter.class,
				JsonReader.class, AuthenticationFilter.class, MetricsDynamicFeature.class);

	}
}
