import pandas as pd
import numpy as np
import json
from pathlib import Path
import os

print("Current working directory:", os.getcwd())

def load_config():
    """Load configuration from JSON file."""
    with open('config.json', 'r') as config_file:  # Direct path since config is in the same directory
        config = json.load(config_file)
    return config

def load_data(file_path):
    """Load the dataset from a CSV file."""
    df = pd.read_csv(file_path, dtype={
        'ObjectId': 'int64',
        'CourseID': 'int64',
        'CourseName': 'object',
        'SaleID': 'int64',
        'SaleDate': 'object',  # Will convert to datetime later
        'ReportDate': 'object',
        'ItemID': 'int64',
        'ItemDescription': 'object',
        'ActualPrice': 'object',  # Will convert to numeric later
        'Quantity': 'object',  # Will convert to numeric later
        'SalesTax': 'object',
        'LMPField': 'object'
    }, low_memory=False)
    return df

def preprocess_data(df):
    """Preprocess the data: clean and convert data types."""
    df.columns = df.columns.str.strip()  # Strip whitespace from headers
    df['SaleDate'] = pd.to_datetime(df['SaleDate'], utc=True).dt.tz_localize(None)  # Convert SaleDate to datetime and remove timezone info

    # Convert ActualPrice and Quantity to numeric, forcing errors to NaN
    df['ActualPrice'] = pd.to_numeric(df['ActualPrice'].str.strip(), errors='coerce')
    df['Quantity'] = pd.to_numeric(df['Quantity'].str.strip(), errors='coerce')

    # Print rows where conversion resulted in NaN
    print("Rows with NaN values after conversion:")
    print(df[df['ActualPrice'].isna() | df['Quantity'].isna()])

    # Fill NaN values with 0
    df['ActualPrice'] = df['ActualPrice'].fillna(0)
    df['Quantity'] = df['Quantity'].fillna(0)

    return df

def calculate_metrics(df):
    """Calculate the required metrics."""
    total_sales_amount = df['ActualPrice'] * df['Quantity']
    df['TotalPrice'] = total_sales_amount

    best_selling_product = df.groupby('ItemDescription')['Quantity'].sum().idxmax()
    best_selling_quantity = df.groupby('ItemDescription')['Quantity'].sum().max()

    df['Month'] = df['SaleDate'].dt.to_period('M').astype(str)  # Convert to string to avoid JSON serialization issues
    monthly_sales = df.groupby('Month')['TotalPrice'].sum().reset_index()

    return total_sales_amount.sum(), best_selling_product, best_selling_quantity, monthly_sales

def save_to_json(total_sales_amount, best_selling_product, best_selling_quantity, monthly_sales, output_path):
    """Save the processed data and results to a JSON file."""
    full_output_path = Path(output_path)
    full_output_path.parent.mkdir(parents=True, exist_ok=True)

    processed_data = {
        'total_sales_amount': total_sales_amount,
        'best_selling_product': best_selling_product,
        'best_selling_quantity': best_selling_quantity,
        'monthly_sales': monthly_sales.to_dict(orient='records')
    }

    with open(full_output_path, 'w') as f:
        json.dump(processed_data, f)
    print(f"Processed data saved to '{full_output_path}'")

def main():
    config = load_config()
    df = load_data(config['data_file_path'])
    df = preprocess_data(df)
    total_sales_amount, best_selling_product, best_selling_quantity, monthly_sales = calculate_metrics(df)
    save_to_json(total_sales_amount, best_selling_product, best_selling_quantity, monthly_sales, config['output_file_path'])

if __name__ == "__main__":
    main()
