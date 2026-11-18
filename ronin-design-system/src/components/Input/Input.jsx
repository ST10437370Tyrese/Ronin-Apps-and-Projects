import './Input.css';

export default function Input({ label, type = 'text', placeholder, value, onChange }) {
  return (
    <div className="input-group">
      <label>{label}</label>
      <input 
        type={type} 
        placeholder={placeholder} 
        value={value} 
        onChange={onChange} 
      />
    </div>
  );
}