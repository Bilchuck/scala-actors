## Job Queue
 
[job queue](https://en.wikipedia.org/wiki/Job_queue)!
 
In our case we're going to have `JobExecutor` actor that accepts incoming job requests and executes them using pool of `JobWorker` actors. If one of the `JobWorker` actors fails `JobExecutor` should restart failed job. Also `JobWorker`s should communicate results to `JobExecutor`.

