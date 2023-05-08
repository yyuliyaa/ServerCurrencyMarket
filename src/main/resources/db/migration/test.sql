select username, role, amount
from client
         inner join transaction t on client.user_id = t.user_id
where t.user_id = 11;

insert into client
values (1, 'ads', 'asd', true, 12.5, 'ADMIN');

insert into transaction
values (2, 'buy', 1, 2, 11, CURRENT_DATE);

insert into company
values (2, 'LITCOIN', 'a askmdkas', 123, 2);

update client
set activated = 'ACTIVE'
where user_id between 11 and 21;

select username, cash, role, status_type, counter_of_stocks, company_name
from client
         inner join transaction t on client.user_id = t.user_id
         inner join company c on t.company_id = c.company_id
where t.user_id = 1;

UPDATE client
SET activated = 'ACTIVE'
where user_id between 1 and 30;

insert into company (company_id, company_name, company_info, stock_price, counter_of_stocks, owner_id, company_status)
VALUES  (4,'BIT','asd',12.4,123123, 13 ,'WAITING');

select username, company_name, company_status
from company inner join client c on company.owner_id = c.user_id;

Update company
SET company_status = 'CONFIRMED'
where company_name = 'BIT';

insert into client (user_id, username, password, activated, cash, role, status_type, company_id) VALUES (?,?,?,?,?,?,?,?)


insert into chat (chat_id, user_id, message, date)
VALUES (2, 10, 'Hello everyone', CURRENT_TIME);

insert into transaction (transaction_id, transactional_type, user_id, company_id, amount, date)
values (1,'BUY',10, null,111, CURRENT_TIME);

SELECT * from transaction where user_id = 16