$env:PGPASSWORD='postgres'
psql -f create_user.sql -U postgres
$env:PGPASSWORD='library'
psql -f create_db.sql -U library -d postgres
psql -f create_tables.sql -U library -d library
