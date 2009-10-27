package org.sonatype.maven.plugin.seleniumgrid;

import org.apache.maven.execution.MavenSession;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.sonatype.maven.plugin.seleniumgrid.util.SeleniumUtil;

/**
 * @author velo
 * @goal stop-grid
 * @phase post-integration-test
 */
public class StopSeleniumGridMojo
    extends AbstractMojo
{

    /**
     * @parameter default-value="${session}"
     * @required
     * @readonly
     */
    private MavenSession session;

    public void execute()
        throws MojoExecutionException, MojoFailureException
    {
        String[] ports = session.getExecutionProperties().getProperty( "selenium-ports" ).split( "," );
        try
        {
            for ( int i = ports.length - 1; i >= 0; i-- )
            {
                int port = Integer.parseInt( ports[i] );
                if ( i == 0 )
                {
                    SeleniumUtil.stopHub( port );
                }
                else
                {
                    SeleniumUtil.stopRC( port );
                }
            }

        }
        catch ( Exception e )
        {
            throw new MojoExecutionException( e.getMessage(), e );
        }
    }

}
