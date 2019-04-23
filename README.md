# Order Tracking Application

Exam project for 2nd semester at Business Academy Southwest in cooperation with Belman A/S.

Time for completion: 5 weeks

Problem statement from Belman A/S :

We require an application which will be able to display the current progress of our production orders. The application will primarily be used as display which updates throughout the day with relevant orders to the current time.

We would like to see the following data on the screen for each order:

-        Order number – This can be presented as (01505001 or 015-050-01)

-        Delivery date – Should only display the date not time of the day.

-        Customer

-        Department

-        Estimated progress

-        Realized progress in relation to finished assignments on the order.

-        Overview of other departments and their progress.

-        The worker(s) currently active on the order or the last sign into it.

The application should be able to present multiple orders looking like postIt's or similar structure. Each order must be presented on the screen at the point in time where the estimated start date is reached for the given department. The order must stay on the screen until the given department has finished their assignments of the order.

Most departments work sequential to each other as the departments are dependent on the work of other departments to start their work. As an example Department1(D1) is a dependent of Department2(D2), this means that before D2 can start their work D1 has to be finished. Therefore, are each department’s estimated time shifted from one another to allow this behavior. If D1 starts their work in week 1 and D2 starts in week 2, the order should be displayed at D1 from week 1 until the order has finished for their department. Despite D1’s order state, D2 should always be displayed the order in week 2 – D2 will then be able to see if D1 has finished their work.
