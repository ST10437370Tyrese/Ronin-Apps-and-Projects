

using System.Diagnostics;
namespace ASPExample1
{
    public class Program
    {
        public static void Main(string[] args)
        {
            //var builder = WebApplication.CreateBuilder(args);
            //var app = builder.Build();

            //app.MapGet("/", () => "Hello World!");

            //app.Run();

            ///*********************

            //var builder = WebApplication.CreateBuilder(args);
            //var app = builder.Build();

            //app.MapGet("/", () =>
            //{
            //    var process = Process.GetCurrentProcess();

            //    return $"Hello World!\n" +
            //    $"Process ID: {process.Id}\n" +
            //    $"Process Name: {process.ProcessName}\n" +
            //    $"Start Time: {process.StartTime}\n" +
            //    $"Memory Usage: {process.WorkingSet64 / 1024 / 1024} MB\n" +
            //    $"CPU Time: {process.TotalProcessorTime}";

            //});

            //app.Run();

            //***************************

            var builder = WebApplication.CreateBuilder(args);
            var app = builder.Build();

            app.MapGet("/", async (HttpContext context) =>
            {
                var process = Process.GetCurrentProcess();

                context.Response.ContentType = "text/html";
                await context.Response.WriteAsync($@"
                    <h1>Hello World!</h1> 
                    <ul>
                    <li>Process ID: {process.Id}</li>
                    <li>Process Name: {process.ProcessName}</li>
                    <li>Start Time: {process.StartTime}</li>
                    <li>Memory Usage: {process.WorkingSet64 / 1024 / 1024} MB</li>
                    <li>CPU Time: {process.TotalProcessorTime}</li>
                    </ul>
                ");
            });

            app.Run();

        }
    }
}
